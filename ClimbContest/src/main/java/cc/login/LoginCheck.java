package cc.login;

public class LoginCheck {
	
	private String[] Names = new String[] {"Bobson","Jabba","Drozdo", "Pio","Fell"};
	private String[] Passwords = new String[] {"ukulele12","Orzeszek16","Drozdo123","PioPass","Fell1234"};
	private String name;
	private String password;
	
	
	boolean checkLogin (String enteredName, String enteredPassword) {
		name = "Jabba"; // name = enteredName;
		password = "Orzeszek16"; // password = enteredPassword;		
		boolean loggedIn = false;

		
		
		if (Names.length == Passwords.length) {			
				
		for (int i = 0; i < Names.length; i++) {			
			if (name.equals(Names[i]) && password.equals(Passwords[i])) {
				loggedIn = true; //logowanie poprawne
				}			
		}
		}else System.out.println("Array error :)"); //Tablice haseł i loginów mają różną długość, ryzyko wyjątku
				
			
		
		return loggedIn;	
		
		
	}
	

}
