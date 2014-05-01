ShoppingCart_OpenEjb_Standalone
===============================

This application is a standalone Restful application developed using OpenEJB.

To run this application you need gradle in place.
In the command prompt enter
$gradle run


At the end you should see.
REST Application: http://0.0.0.0:8080/ShoppingCart                               -> org.apache.openejb.server.rest.InternalApplication
     Service URI: http://0.0.0.0:8080/ShoppingCart/carts                         ->  EJB com.nrk.cart.web.resource.CartResource
           DELETE http://0.0.0.0:8080/ShoppingCart/carts/{cartid}/items/{itemid} ->      Response deleteItem(long, long)
           DELETE http://0.0.0.0:8080/ShoppingCart/carts/{id}                    ->      Response deleteCart(long)
              GET http://0.0.0.0:8080/ShoppingCart/carts/                        ->      List<Cart> getAllCarts()
              GET http://0.0.0.0:8080/ShoppingCart/carts/{id}                    ->      Cart getCart(long)
             POST http://0.0.0.0:8080/ShoppingCart/carts/                        ->      Cart createCart(CartItem)
             POST http://0.0.0.0:8080/ShoppingCart/carts/{id}/items              ->      Cart addItem(long, CartItem)
              PUT http://0.0.0.0:8080/ShoppingCart/carts/{id}                    ->      Cart checkOut(long, Cart)
     Service URI: http://0.0.0.0:8080/ShoppingCart/products                      ->  EJB com.nrk.cart.web.resource.ProductResource
           DELETE http://0.0.0.0:8080/ShoppingCart/products/{id}                 ->      Response deleteProduct(long)
              GET http://0.0.0.0:8080/ShoppingCart/products/                     ->      List<Product> getAllProducts()
              GET http://0.0.0.0:8080/ShoppingCart/products/{id}                 ->      Product getProduct(long)
             POST http://0.0.0.0:8080/ShoppingCart/products/                     ->      Product createProduct(Product)
              PUT http://0.0.0.0:8080/ShoppingCart/products/                     ->      Product updateProduct(Product)
			  
Please replace 0.0.0.0 with the host name of the server

