
public class Customer {
	
		public int id;
		public String arrTime;
		public int waiting_time_of_;
		public int waiting_after_nine;
		public Customer next;
		public Customer previous;
		public boolean served;
		
		public Customer(int id, String arrTime) {
			this.id = id;
			this.arrTime = arrTime;
			this.served = false;
			this.waiting_time_of_ = 0;
			this.waiting_after_nine = 0;
		}
		
		
	
}
