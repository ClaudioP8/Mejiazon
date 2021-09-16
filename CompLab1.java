/*
*Author: Claudio Palomares
*Course: CS1101
*Instructor: Dr. Daniel Mejia / IA: Ali Nouri
*Description: Program handles user input and reads a file to simulate shopping experience.
*/

import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;

public class CompLab1{
	public static void main(String[] args) throws InputMismatchException{
		//Initializing variables to be used in the program
		Scanner inputReader = new Scanner(System.in);
		int menuChoice = 0;
		int financingChoice;
		int cartItems = 0;
		double cartTotal = 0;
		double paymentDue = 0;
		double productPrice;
		double giftCardAmount;
		String productName;
		String currentProduct = "";
		final String MAIN_MENU = ("\nMain Menu\n1. Add to cart\n2. View Cart\n3. Clear cart\n4. Checkout\n5. Exit\nEnter menu option: ");

		//While loop iterates while menu choice is not 5 (exit).
		while(menuChoice != 5){
			//Try/catch for main menu choice, can throw InputMismatchException
			try{
				switch(menuChoice){
					//Case 1 handles "Add to cart" feature of program
					case 1:
						//Trying to open and read .txt file
						try{
							File file = new File("Product_Database.txt");
							Scanner fileReader = new Scanner(file);
							System.out.println("");

							//While loop prints .txt file
							while(fileReader.hasNextLine()){
								System.out.println(fileReader.nextLine());
							}//Close while

							//Taking user input for string to be compared to .txt file
							System.out.print("\nWhat would you like to add to cart? (No_spaces | CaSe SeNsItIvE)\n");
							productName = inputReader.next();

							//Resetting scanner
							fileReader = new Scanner(file);

							//User input string is compared to string parsed from file with each iteration
							while(fileReader.hasNextLine()){
								currentProduct = fileReader.next();
								productPrice = fileReader.nextDouble();

								//If executes when user input string matches string on file
								//Number of items in cart and cart total are updated and printed
								if(currentProduct.equals(productName)){
									cartItems++;
									System.out.println("\nAdded to cart!\nItems: " + cartItems);
									cartTotal += productPrice;
									System.out.println("Total: " + cartTotal);
									break; //Exit while loop since string has been matched
								}//Close if
							}//Close while

							//If executes when user input string does not match any parsed string from file
							if(!currentProduct.equals(productName)){
								System.out.println("\nItem not found! Returning to main menu.");
							}//Close if
						}//Close try

						catch(FileNotFoundException e){
							System.out.println("Database not found.");
						}//Close catch
						break;

					//Case 2 handles "view cart" feature of program
					case 2:
						System.out.println("\nItems: " + cartItems + "\nTotal: " + cartTotal);
						break;

					//Case 3 handles "clear cart" feature of program
					case 3:
						cartItems = 0;
						cartTotal = 0;
						System.out.println("\nCart cleared!\nItems: " + cartItems + "\nTotal: " + cartTotal);
						break;

					//Case 4 handles "checkout" feature of program
					case 4:
						if(cartTotal > 0){
							System.out.println("\nItems: " + cartItems + "\nTotal: " + cartTotal);
							System.out.println("\nHow would you like to finance your purchase?");
							System.out.print("0. Pay in full\n1. 6 Month financing\n2. 12 Month financing\nEnter financing option: ");
							financingChoice = inputReader.nextInt();

							//Switch with financing choice received from user
							switch(financingChoice){

								//Case 0 handles "pay in full" option
								case 0:
									paymentDue = cartTotal;
									break;

								//Case 1 handles "6 month financing" option
								case 1:
									paymentDue = ((cartTotal + (cartTotal * 0.01)) / 6);
									break;

								//Case 2 handles "12 month financing" option
								case 2:
									paymentDue = ((cartTotal + (cartTotal * 0.02)) / 12);
									break;

								//default handles any other integer
								default:
									paymentDue = cartTotal;
									System.out.println("\nNot a valid financing option, payment in full due.");
							}//Close switch(financingChoice)

							//Print payment due and receive gift card amount from user
							System.out.printf("\nPayment due: $%.2f\n", paymentDue);
							System.out.print("Enter gift card amount: $");
							giftCardAmount = inputReader.nextDouble();

							if(giftCardAmount < paymentDue){
								System.out.println("\nInsufficient funds, please reload gift card and check out again.");
							}//Close if

							//Else executes when if fails, so giftCardAmount is greater or equal to payment due
							else{
								giftCardAmount -= paymentDue;
								System.out.printf("\nWe have received your order! Remaining gift card balance: $%.2f\n", giftCardAmount);
								cartItems = 0;
								cartTotal = 0;
								System.out.println("\nYour cart is now empty. Stick around and shop some more, or press 5 to exit.");
								break;
							}//Close else
						}
						else{
							System.out.println("\nYou might want to add something to your cart first.");
							break;
						}

					//Default handles any input other than 1-5. Since menuChoice is initialized at 0, it is also the first message displayed.
					default:
						System.out.println("\nPlease enter a number 1-5.");

				}//Close switch

				//After each case is executed, user is prompted for menuChoice.
				System.out.print(MAIN_MENU);
				menuChoice = inputReader.nextInt();

			}//Close try
			//Catch handles an input mismatch, specifically when the user fails to enter an int for the switch.
			catch(InputMismatchException e){
				String badInput = inputReader.nextLine();
				menuChoice = 0;
				System.out.println("\n\"" + badInput + "\" is not a valid menu choice!\nReturning to main menu.");
				continue;
			}
		}//Close while

		//Goodbye message prints when user inputs 5 as menu choice.
		System.out.println("\nThank you for shopping at Mejiazon Prime.");

	}//Close main
}//Close class
