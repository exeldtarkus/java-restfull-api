package co.id.adira.moservice.contentservice;


public class Test {

	public static void main(String[] args) {

		String a = "abc";
		String b = "def";
		
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < Math.max(a.length(), b.length()); i++) {
		    if (i < a.length()) {
		        buf.append(a.charAt(i));
		    }
		    if (i < b.length()) {
		        buf.append(b.charAt(i));
		    }
		}
		final String result = buf.toString();
		System.out.println(result);
		
	}

}
