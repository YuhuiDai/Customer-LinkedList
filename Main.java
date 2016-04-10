import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
public class Main {
	public static CustomerList mylist = new CustomerList();
	

	public static void main(String[] args) {

		try{
			// ########## get info from customer file #############
			Scanner input_customer = new Scanner(new File(args[0]));
			CustomerList.serving_time = Integer.parseInt(input_customer.nextLine());
			
			while (input_customer.hasNext()) {
				input_customer.nextLine();
				
				String s = input_customer.nextLine();
				int id = Integer.parseInt(s.substring(12));
				String arrTime = input_customer.nextLine().substring(14);
			
				mylist.add_Last(id, arrTime);
				
			}	
		
			
			// ##### now iterate through the linkedlist to monitor service
			Customer temp = mylist.first;

			while (temp != null) {
				
				mylist.update_idle_break(temp);
				mylist.served(temp);
				System.out.println("Customer "+ temp.id + " is " + temp.served);
				System.out.println(temp.waiting_time_of_);
				System.out.println(mylist.current_time + " is the current time");
				temp = temp.next;
				
			}
			
			mylist.maximum_number_of_people_in_queue_at_any_time = mylist.max_num_in_line();
			System.out.println(mylist.number_of_customers_served);
			System.out.println(mylist.longest_break_length);
			System.out.println(mylist.total_idle_time);
			System.out.println(mylist.maximum_number_of_people_in_queue_at_any_time);
			// ######### get questions from queries file ##############
			Scanner input_queries = new Scanner(new File(args[1]));
			
			// *****output.txt is professor's file***my result is going to be printed in output2*** just to check
			PrintWriter j = new PrintWriter(new File("output2.txt"));
			while (input_queries.hasNextLine()) {
				
				String str = input_queries.nextLine();
				String[] ask = str.split(" ");
//				System.out.println(ask.length);
				String replacedStr = str.toLowerCase().replaceAll("-", "_");
				if (ask.length == 1 ) {
					if (replacedStr.equals("number_of_customers_served")) {
						j.print(str + ":" + mylist.number_of_customers_served + "\n");
					} else if (replacedStr.equals("longest_break_length")) {
						j.print(str + ":" + mylist.longest_break_length + "\n");
					} else if (replacedStr.equals("total_idle_time")) {
						j.print(str + ":" + mylist.total_idle_time + "\n");
					} else if (replacedStr.equals("maximum_number_of_people_in_queue_at_any_time")) {
						j.print(str + ":" + mylist.maximum_number_of_people_in_queue_at_any_time + "\n");
					} else {
						System.out.println("error");
					}
					
				} else if (ask.length == 2) {
					int num = mylist.get_waiting_time_based_on_id(Integer.parseInt(ask[1]));
					j.print(str + ":" + num + "\n");
				} else {
					System.out.println("Error");
				}
				
				
			}
			 
			
			j.close();
			
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}