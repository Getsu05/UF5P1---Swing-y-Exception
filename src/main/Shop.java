package main;


import model.Amount;
import model.Employee;
import model.Product;
import model.Sale;
import model.Client;
import java.util.Scanner;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import view.LoginView;


public class Shop {
	public Amount cash = new Amount(100.00);
	//private double cash = 100.00;
	//private Product[] inventory;
	ArrayList<Product> inventory = new ArrayList<Product>();
	ArrayList<Sale> sales = new ArrayList<Sale>();
	LocalDateTime tiempo = LocalDateTime.now();
	File cargaInventario = new File("inputInventory.txt");
		private int numberProducts;
	//private Sale[] sales;

	final static double TAX_RATE = 1.04;

	public Shop() {		
		//inventory = new Product[10];
		inventory = new ArrayList<Product>();
		//sales = new Sale[10];
		sales = new ArrayList<Sale>();
	}
	
	public static void main(String[] args) {
            
            LoginView login2 = new LoginView();
            
            login2.setVisible(true);
            
            
            
		Shop shop = new Shop();

		shop.loadInventory();

		Scanner scanner = new Scanner(System.in);
		int opcion = 0;
		boolean exit = false;

		
		int usuario;
		String contraseña;
		
		//BUCLE DE LOGIN
		
		
		
		shop.initSession();
                
               

		do {
			System.out.println("\n");
			System.out.println("===========================");
			System.out.println("Menu principal miTienda.com");
			System.out.println("===========================");
			System.out.println("1) Contar caja");
			System.out.println("2) Añadir producto");
			System.out.println("3) Añadir stock");
			System.out.println("4) Marcar producto proxima caducidad");
			System.out.println("5) Ver inventario");
			System.out.println("6) Venta");
			System.out.println("7) Ver ventas");
			System.out.println("8) Eliminar producto");
			System.out.println("9) Salir programa");
			System.out.print("Seleccione una opción: ");
			opcion = scanner.nextInt();

			switch (opcion) {
			case 1:
				shop.showCash();
				break;

			case 2:
				shop.addProduct();
				break;

			case 3:
				shop.addStock();
				break;

			case 4:
				shop.setExpired();
				break;

			case 5:
				shop.showInventory();
				break;

			case 6:
				shop.sale();
				break;

			case 7:
				shop.showSales();
				break;
				
			case 8:
				shop.removeItem();
				break;
				
			case 9:
				exit = true;
				break;
				
			}

		} while (!exit);

	}

	
	
	public void initSession() {
            int usuario = 0;
            String password = null;
            boolean login = false;
            Scanner sc = new Scanner(System.in);

            do {

                Employee empleado = new Employee(password);
                System.out.print("INTRODUZCA EL NUMERO DE EMPLEADO: ");
                usuario = sc.nextInt();

                System.out.print("INTRODUZCA LA CONTRASEÑA: ");
                password = sc.next();

                login = empleado.check_login(usuario, password);

                if (!login) {
                    System.out.println("Intentalo de nuevo:");
                }
            } while (!login);

            if (login) {
                System.out.println("LOGIN CORRECTO");
            }
        }

	
	
	
	/**
	 * load initial inventory to shop
	 */
	public void loadInventory() {
		//addProduct(new Product("Manzana", 10.00, true, 10, 20.00));
		//addProduct(new Product("Pera", 20.00, true, 20, 40.00));
		//addProduct(new Product("Hamburguesa", 30.00, true, 30, 60.00));
		//addProduct(new Product("Fresa", 5.00, true, 20, 10.00));
		File file = new File ("./files/inputInventory.txt");
		
		if(!file.exists()) {
			System.out.println("No existe ningún fichero");
		}
		Scanner myReader;
		
		try {
			
			myReader = new Scanner(file);
			
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				
				String[] text = data.split(";");
				
				String productName = text[0];
				
				String price = text[1];
				
				String stock = text[2];
				
				String[] textProduct = productName.split(":");
				
				String nameProduct = textProduct[1];
				
				String[] textPrice = price.split(":");
				
				Double namePrice = Double.parseDouble(textPrice[1]);
				
				String[] textStock = stock.split(":");
				
				int nameStock = Integer.parseInt(textStock[1]);
				
				//addProduct(new Product(nameProduct,(new Amount (namePrice * 2)), (new Amount (nameProduct)), true, nameStock));
				addProduct(new Product(nameProduct, namePrice * 2, true, nameStock, namePrice * 2));
				
				
			}
		
		}  catch (FileNotFoundException e) {
			// TODO: handle exception
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

	/**
	 * show current total cash
	 */
	private void showCash() {
		
		System.out.println("Dinero actual: " + cash);
	}

	/**
	 * add a new product to inventory getting data from console
	 */
	public void addProduct() {
		if (isInventoryFull()) {
			System.out.println("No se pueden añadir más productos");
			return;
		}
		Scanner scanner = new Scanner(System.in);
		System.out.print("Nombre: ");
		String name = scanner.nextLine();
		System.out.print("Precio mayorista: ");
		double wholesalerPrice = scanner.nextDouble();
		double publicPrice = wholesalerPrice * 2;
		System.out.print("Stock: ");
		int stock = scanner.nextInt();

		addProduct(new Product(name, wholesalerPrice, true, stock, publicPrice));
	}

	/**
	 * add stock for a specific product
	 */
	public void addStock() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Seleccione un nombre de producto: ");
		String name = scanner.next();
		Product product = findProduct(name);

		if (product != null) {
			// ask for stock
			System.out.print("Seleccione la cantidad a añadir: ");
			int stock = scanner.nextInt();
			// update stock product
			product.setStock(stock + product.getStock());
			System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getStock());

		} else {
			System.out.println("No se ha encontrado el producto con nombre " + name);
		}
	}

	/**
	 * set a product as expired
	 */
	private void setExpired() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Seleccione un nombre de producto: ");
		String name = scanner.next();

		Product product = findProduct(name);

		if (product != null) {
			System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.expire());

		}
	}

	/**
	 * show all inventory
	 */
	public void showInventory() {
		System.out.println("Contenido actual de la tienda:");
		for (Product product : inventory) {
			if (product != null) {
				System.out.println(" " + product.toString());
			}
		}
	}

	/**
	 * make a sale of products to a client
	 */
	public void sale() {
	    // Pedir el nombre del cliente
	    Scanner sc = new Scanner(System.in);
	    System.out.println("Realizar venta, escribir nombre cliente");
	    String clientName = sc.nextLine();

	    // Crear una instancia de Client con un saldo predeterminado
	    Client client = new Client(clientName, 123, new Amount(50.0));

	    // Crea una lista para almacenar los productos vendidos
	    ArrayList<Product> soldProducts = new ArrayList<>();

	    // Itera hasta que se ingrese '0' como nombre de producto
	    double totalAmount = 0.0;
	    String productName;
	    do {
	        System.out.println("Introduce el nombre del producto, escribir 0 para terminar:");
	        productName = sc.nextLine();

	        if (!productName.equals("0")) {
	            // Busca el producto en el inventario
	            Product product = findProduct(productName);
	            if (product != null && product.isAvailable()) {
	                soldProducts.add(product);
	                totalAmount += product.getPublicPrice() * TAX_RATE;
	                
	                product.setStock(product.getStock() - 1);
	                if (product.getStock() == 0) {
	                    product.setAvailable(false);
	                }
	                System.out.println("Producto añadido con éxito");
	            } else {
	                System.out.println("Producto no encontrado o sin stock");
	            }
	            client.pay(cash);
	        }
	    } while (!productName.equals("0"));
	    
	    

	    // Actualizar el saldo de caja
	    cash.setValue(cash.getValue() + totalAmount);
	    
	    

	    // Crear una instancia de Sale y agregarla a la lista de ventas
	    Sale sale = new Sale(client, soldProducts, totalAmount, LocalDateTime.now());
	    sales.add(sale);

	    System.out.println("Venta realizada con éxito, total: " + totalAmount);
	}

	/**
	 * show all sales
	 */
	private void showSales() {
	    System.out.println("Lista de ventas:");
	    for (Sale sale : sales) {
	        if (sale != null) {
	            System.out.println(sale.toString());
	        }
	    }
	    if (sales == null) {
	        System.out.println("No se ha realizado ninguna venta aún");
	    }

	    Scanner sc = new Scanner(System.in);
	    System.out.println("Desea guardar la venta en un fichero? (S/N)");
	    String scanner = sc.nextLine();

	    if (scanner.equalsIgnoreCase("N")) {
	        System.out.println("Las ventas no se han guardado en un fichero");
	    }

	    if(scanner.equalsIgnoreCase("S")) {
	        LocalDate currentDate = LocalDate.now();
	        String formattedDate = currentDate.toString(); 
	        String fileName = "files/sales_" + formattedDate + ".txt";
	        
	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
	            for (Sale sale : sales) {
	                if (sale != null) {
	                    writer.write(sale.toString());
	                    writer.newLine();
	                }
	            }
	            System.out.println("Las ventas se han guardado en el archivo: " + fileName);
	        } catch (IOException e) {
	            System.out.println("Error al guardar las ventas en el archivo.");
	            e.printStackTrace();
	        }
	    }
	}		
	
	private void removeItem() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Que producto desea eliminar: ");
		String name = scanner.next();
		Product product = findProduct(name);
		if (product != null) {
			inventory.remove(product);
			System.out.println("Producto eliminado");
		}else {
			System.out.println("Producto no encontrado");
		}
		
		
	}

	/**
	 * add a product to inventory
	 * 
	 * @param product
	 */
	public void addProduct(Product product) {
		if (isInventoryFull()) {
			System.out.println("No se pueden añadir más productos, se ha alcanzado el máximo de " + inventory.size());
			return;
		}
		//inventory[numberProducts] = product;
		inventory.add(product);
		//numberProducts++;
	}

	/**
	 * check if inventory is full or not
	 */
	public boolean isInventoryFull() {
		if (numberProducts == 10) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * find product by name
	 * 
	 * @param product name
	 */
	public Product findProduct(String name) {
		for (int i = 0; i < inventory.size(); i++) {			
			if (inventory.get(i) != null && inventory.get(i).getName().equalsIgnoreCase(name)) {
				return inventory.get(i);
			}
		}
		return null;

	}

}