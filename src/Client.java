import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Client implements ActionListener {

    static JPanel a1;
    JTextField text;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    public Client(){
        f.setLayout(null);

        JPanel p1 = new JPanel();                             // Green Panel in Heading
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0,0,380,60);              //p1.setBounds(0,0,450,60);
        p1.setLayout(null);
        f.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));      // Back Button.
        Image i2 = i1.getImage().getScaledInstance(25,25,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(404);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/Robot.png"));      // Profile Picture Gaithonde
        Image i5 = i4.getImage().getScaledInstance(50,50,Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40,5,50,50);
        p1.add(profile);

//        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));      // Video Icon
//        Image i8 = i7.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
//        ImageIcon i9 = new ImageIcon(i8);
//        JLabel video = new JLabel(i9);
//        video.setBounds(280,15,30,30);                                 //  video.setBounds(330*,15,30,30);
//
//        p1.add(video);
//
//        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));      // Phone Icon
//        Image i11 = i10.getImage().getScaledInstance(30,30,Image.SCALE_DEFAULT);
//        ImageIcon i12 = new ImageIcon(i11);
//        JLabel phone = new JLabel(i12);
//        phone.setBounds(320,16,30,30);                            // phone.setBounds(380,16,30,30);
//        p1.add(phone);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));      // Setting Icon
        Image i14 = i13.getImage().getScaledInstance(10,25,Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel settings = new JLabel(i15);
        settings.setBounds(360 ,15,10,30);                      //settings.setBounds(430 ,15,10,30);
        p1.add(settings);

        JLabel name = new JLabel("ChatBot");                     // Name Tag
        name.setBounds(100, 15, 100, 18);                   //name.setBounds(100, 15, 150*, 18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(name);

        JLabel status = new JLabel("Active Now");                   // Status Tag
        status.setBounds(100, 35, 100, 18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 12));
        p1.add(status);

        a1 = new JPanel();                                            // Main Middle Panel
        a1.setBounds(5,65,370,535);                        //  a1.setBounds(5,65,440*,535);

        f.add(a1);

        text = new JTextField();                                 // Text Area
        text.setBounds(5, 605, 270, 45);                 //text.setBounds(5, 605, 310, 45);
        text.setFont(new Font("SAN_SERIF", Font.BOLD, 16));
        f.add(text);

        JButton send = new JButton("Send");                            // Send Button
        send.setBounds(280, 605, 95, 43);                 //send.setBounds(320, 605, 123, 45);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.setFont(new Font("SAN_SERIF", Font.BOLD, 17));
        send.addActionListener(this);
        f.add(send);

        f.setSize(380,650);                                 //setSize(450,650);
        f.setLocation(700,20);
        f.getContentPane().setBackground(Color.WHITE);
        f.setUndecorated(true);
        f.setVisible(true);       // It should be in the end of constructor so that it can apply all the attributes to the pane.
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String out = text.getText();
            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);

            dout.writeUTF(out);

            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style = \"width: 120px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37,211,102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(10,15,10,40));

        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);

        return panel;
    }
    public static void main(String[] args){
        new Client();

        try{
            Socket s = new Socket("127.0.0.1", 1234);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());

            while (true){
                a1.setLayout(new BorderLayout());
                String msg = din.readUTF();
                JPanel panel = formatLabel(msg);

                JPanel left = new JPanel(new BorderLayout());
                left.add(panel, BorderLayout.LINE_START);
                vertical.add(left);

                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical, BorderLayout.PAGE_START);

                f.validate();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}