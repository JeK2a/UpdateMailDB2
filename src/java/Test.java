import javax.mail.*;
import javax.mail.search.SearchTerm;
import java.util.Properties;
import java.util.Scanner;

public class Test {
    public static void main(String args[]) {
//        Scanner sc = new Scanner(System.in);
        final String login     = "jek2ka@gmail.com";
        final String password = "pbnokia3510";
        String host           = "smtp.gmail.com";
        try {
            Properties prop_tmp = new Properties();
            prop_tmp.put("mail.smtp.host", "smtp.gmail.com");
            prop_tmp.put("mail.smtp.socketFactory.port", "465");
            prop_tmp.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            prop_tmp.put("mail.smtp.auth", "true");
            prop_tmp.put("mail.smtp.port", "465");

            Session session = Session.getDefaultInstance(
                    prop_tmp,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication(){
                            return new PasswordAuthentication(login, password);
                        }
                    });
            Store store = session.getStore("imaps");
            store.connect(host, login, password);
            Folder folder_box = store.getFolder("INBOX");


            Folder folder = store.getFolder("whatever");


//            f.getUnreadMessageCount()
//            UIDFolder uid_folder = (UIDFolder) folder;
//            Message[] newMsgs = uid_folder.getMessagesByUID(lastSeenUID + 1, UIDFolder.MAXUID);

            folder_box.open(Folder.READ_ONLY);
            SearchTerm search = new SearchTerm() {
                @Override
                public boolean match(Message message) {
                    try {
                        if (message.getSubject().contains("thandebert")) { return true; }
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                    return false;
                }
            };

            Message[] found = folder_box.search(search);

            int length = found.length;

            for (int i = 0; i < found.length; i++) {
                Message mess1 = found[i];
                System.out.println("->Message Number > "+i);
                System.out.println("->Message Subject >"+mess1.getSubject());
            }
            folder_box.close(true);
            store.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}