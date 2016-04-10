import java.util.NoSuchElementException;

public class CustomerList {
	public static int number_of_customers_served;
	public static int longest_break_length;
	public static int total_idle_time;
	public static int max_waiting_time;
	public static int current_time;
	public static int serving_time;
	public Customer first;
	public static int maximum_number_of_people_in_queue_at_any_time;
	
	public CustomerList() {
		current_time = 32400;
		first = null;
		longest_break_length = 0;
		total_idle_time = 0;
		max_waiting_time = 0;
	}
	
	
// questions: do i have to deque? if so, how would i eventually answer questions about specific waiting time?
// if not deque, max in queue is hard to get...so how to make the flow
	
	public static void updateMaxWait (Customer curr) {
		
		if (toSeconds(curr.arrTime) < 32400) {
			if (max_waiting_time < curr.waiting_after_nine) {
				max_waiting_time = curr.waiting_after_nine;
			}
		} else {
		
			if (max_waiting_time < curr.waiting_time_of_) {
				max_waiting_time = curr.waiting_time_of_;
			}
		}
	}
	
	public static void updateCurrentTime (Customer curr) {
		//System.out.println(toSeconds(curr.arrTime) + " arrives");
		if (curr.id == 1) {
			if (toSeconds(curr.arrTime) > current_time) {
				current_time = toSeconds(curr.arrTime) + serving_time;
			} else {
				current_time = current_time + serving_time;
			}
			
		} else {
			if (toSeconds(curr.arrTime) > current_time) {
				if (curr.waiting_time_of_ == 0) {
					current_time = toSeconds(curr.arrTime) + serving_time;
				} else {
					current_time = toSeconds(curr.arrTime) + curr.waiting_time_of_ + serving_time;
				}
			} else {
				//System.out.println(curr.waiting_time_of_ + " herereeee");
				current_time = toSeconds(curr.arrTime) + curr.waiting_time_of_ + serving_time;
			}
		}
	}
	
	public static void served(Customer curr) {
		int arriveTime = toSeconds(curr.arrTime);
		// no longer take customers after 5
		if (arriveTime > 61200) {
			curr.served = false;
			curr.waiting_time_of_ = 0;
		}
		
		if (current_time > 61200) {
			curr.served = false;
			curr.waiting_time_of_ = 61200 - arriveTime;
		}else {
			curr.served = true;
			number_of_customers_served ++;
			if (curr.id == 1) {
				curr.waiting_after_nine = 0;
				if (arriveTime > current_time) {
					curr.waiting_time_of_ = 0;
				} else {
					curr.waiting_time_of_ = current_time - arriveTime;
				}
			} else {
				
				if (current_time > arriveTime) {
					curr.waiting_time_of_ = current_time - arriveTime;
				} 
				
				if (arriveTime < 32400) {
					curr.waiting_after_nine = curr.waiting_time_of_ - (32400 - arriveTime);
				} else {
					curr.waiting_after_nine = curr.waiting_time_of_;
				}
			}
			//System.out.println("waiting: " + curr.waiting_time_of_);
			updateCurrentTime(curr);
			updateMaxWait(curr);
			
		}
		
	}
	
	public static void update_idle_break(Customer curr) {
		int arriveTime = toSeconds(curr.arrTime);
		
		if (arriveTime > current_time) {
			// update idle time
			int breaktime = arriveTime - current_time;
			total_idle_time += breaktime;
			// compare and update longest break
			if (breaktime > longest_break_length) {
				longest_break_length = breaktime;
			}
		}
		
	}
	
	
	
	public int get_waiting_time_based_on_id(int id) {
		Customer temp = first;
		if (temp == null) {
			throw new NoSuchElementException();
		} 
		while (temp.id != id){
			temp = temp.next;
		}
		return temp.waiting_time_of_;
		
	}
	
	
	public void add_Last(int id, String arrTime) {
		Customer temp = first;
		if (first == null) {
			Customer newcustomer = new Customer(id, arrTime);
			first = newcustomer;
			newcustomer.next = null;	
		} else {
			while (temp.next != null) {
				temp = temp.next;
			}
			Customer newcustomer = new Customer(id, arrTime);
			temp.next = newcustomer;
			newcustomer.next = null;
		}
		
	}
	
	public int max_num_in_line () {
		// using what the max serving time (consequtively) to calculate how many people are in line, 
		//plus the one last served, whish is not counted in this serving time frame
		int ppl = max_waiting_time/serving_time + 1;
		return ppl;
	}
	
	
	public static int toSeconds(String s) {
	    String[] hourMinSec = s.split(":");
	    int hour = Integer.parseInt(hourMinSec[0]);
	    // change the 2:00 to 14:00
	    if (hour > 0 && hour < 6) {
	    	hour = hour + 12;
	    }
	    int mins = Integer.parseInt(hourMinSec[1]);
	    int secs = Integer.parseInt(hourMinSec[2]);
	    int final_time = hour * 3600 + mins* 60 + secs;
	    return final_time;
	}
	
	
	public boolean hasNext() {
		Customer temp = first;
		if (temp == null) {
			return false;
		} else {
			return temp.next != null;
		}
	}
	
	public boolean hasPrevious() {
		Customer temp = first;
		if (temp == null) {
			return false;
		} else {
			return temp.previous != null;
		}
	}
	
	
	
	
}
