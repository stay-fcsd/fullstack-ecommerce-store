package com.abranlezama.ecommercestore.service.imp;

import com.abranlezama.ecommercestore.dto.cart.CartDTO;
import com.abranlezama.ecommercestore.dto.cart.mapper.CartMapper;
import com.abranlezama.ecommercestore.exception.CustomerNotFound;
import com.abranlezama.ecommercestore.exception.ExceptionMessages;
import com.abranlezama.ecommercestore.exception.ProductNotFoundException;
import com.abranlezama.ecommercestore.model.Cart;
import com.abranlezama.ecommercestore.model.CartItem;
import com.abranlezama.ecommercestore.model.Product;
import com.abranlezama.ecommercestore.repository.CartRepository;
import com.abranlezama.ecommercestore.repository.ProductRepository;
import com.abranlezama.ecommercestore.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImp implements CartService {

    private final CartMapper cartMapper;
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;

    @Override
    public CartDTO getCustomerCart(String userEmail) {
        return cartMapper.mapCartToDto(retrieveCustomerCart(userEmail));
    }

    @Override
    public void addProductToCart(String userEmail, long productId, int quantity) {
        // retrieve customer cart
        Cart cart = retrieveCustomerCart(userEmail);

        // increase product quantity if it is in cart already
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId() == productId)
                .findFirst()
                .orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cart.setTotalCost(computeCartTotal(cart));
            cartRepository.save(cart);
            return;
        }
        // add new product to cart
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(ExceptionMessages.PRODUCT_NOT_FOUND));

        // save cart item
        CartItem newCartItem = new CartItem(product, cart, quantity);
        cart.getCartItems().add(newCartItem);
        cart.setTotalCost(computeCartTotal(cart));
        cartRepository.save(cart);
    }

    @Override
    public void updateCartProduct(String userEmail, long productId, int quantity) {
        // get customer cart
        Cart cart = retrieveCustomerCart(userEmail);

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId() == productId)
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException(ExceptionMessages.PRODUCT_NOT_FOUND));

        // update customer cart
        cartItem.setQuantity(quantity);
        cart.setTotalCost(computeCartTotal(cart));
        cartRepository.save(cart);
    }

    private float computeCartTotal(Cart cart) {
        return cart.getCartItems().stream()
                .map(item -> item.getQuantity() * item.getProduct().getPrice())
                .reduce(0F, Float::sum);
    }

    private Cart retrieveCustomerCart(String userEmail) {
        return cartRepository.findByCustomer_User_Email(userEmail)
                .orElseThrow(() -> new CustomerNotFound(ExceptionMessages.CUSTOMER_NOT_FOUND));
    }

}
