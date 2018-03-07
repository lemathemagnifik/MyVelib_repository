package myVelib;

/**This class describes the message, that users can receive after suscribing to a card or a station. It has one attribute read that 
 * contains the information of if this message has been read by the user.
 */
public class Message {
	

		@Override
		public String toString() {
			return "Message [read=" + read + ", text=" + text + "]";
		}

		//attributes
		/**
		 * this boolean represents if the message has been read
		 */
		private boolean read;
		private String text;
		
		/**
		 * @return the read attribute
		 */
		public boolean isRead() {
			return read;
		}
		/**
		 * @param read the value to set
		 */
		public void setRead(boolean read) {
			this.read = read;
		}
		/**
		 * @return the text
		 */
		public String getText() {
			return text;
		}
		/**
		 * @param text the text to set
		 */
		public void setText(String text) {
			this.text = text;
		}
		
		public Message(String text){
			super();
			this.text= text;
			this.read=false;
			System.out.println("A new message is created!");
		}

}
