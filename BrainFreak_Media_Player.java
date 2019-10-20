import javax.swing.*;
import java.awt.*;
import java.applet.AudioClip;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.*;
public class BrainFreak_Media_Player extends JFrame implements ActionListener
{
    int i=0;
    int plays=0;//governes the functionality of the buttons
    int r=0;//governes the functionality of the File Manager
    int nxt=0;//governes the functionality of the  next and previous buttons
    int j=0;//selects the song to be played
    int check=0;//governes the functionality of the File Manager
    AudioFormat audioFormat;//audio format
    File soundFile;//selects the file to be played
    AudioInputStream audioInputStream;//inputs the audio in the programme
    SourceDataLine sourceDataLine;//feeds the song in binary in the speaker 
    boolean stopPlayback = false;//stops the playback
    JFileChooser files=new JFileChooser();//chooses the file to be played
    File fileArray[]=new File[100];//for playlist
    ImageIcon image[]={new ImageIcon("data/rock2.jpg"),new ImageIcon("data/eq.jpg"),new ImageIcon("data/rock.jpg"),new ImageIcon("data/music.jpg"),new ImageIcon("data/color.jpg")};//for background images
    ImageIcon img=new ImageIcon("data/play.png");//play icon
    ImageIcon img2=new ImageIcon("data/stop.png");//stop icon
    ImageIcon img3=new ImageIcon("data/next.jpg");//next icon
    ImageIcon img4=new ImageIcon("data/prev.png");//previous icon
    ImageIcon openFILE=new ImageIcon("data/open.png");//open file icon
    JButton but=new JButton(image[0]);//background image
    JButton play=new JButton(img);//play button
    JButton stop=new JButton(img2);//stop button
    JButton next=new JButton(img3);//next button
    JButton prev=new JButton(img4);//previous button
    JButton open=new JButton("open",openFILE);//open file button
    JToolBar control=new JToolBar();//control panel
    JPanel master=new JPanel();//master panel
    String nowPlaying="";//updates the information of the file played
    String songs[]=new String[1000];//playlist
    public BrainFreak_Media_Player()
    {
        super("BrainFreak Media Player");//title
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            System.out.println("Can’t set look and feel:"+e.getMessage());
            e.printStackTrace();
        }
        play.setEnabled(false);
        stop.setEnabled(false);
        prev.setEnabled(false);
        next.setEnabled(false);
        setSize(440,505);
        setLocation(415,125);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        play.addActionListener(this);
        stop.addActionListener(this);
        prev.addActionListener(this);
        next.addActionListener(this);
        open.addActionListener(this);
        control.add(play);
        control.add(stop);
        control.add(prev);
        control.add(next);
        control.add(open);
        master.add(but);
        master.add(control);
        master.setVisible(true);
        setResizable(false);
        setVisible(true);
        add(master);
    }

    public static void main(String args[])//calls all the other components
    {
        Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(null,"HELLO AND WELCOME TO BrainFreak Media Player TM\nInstructions are as follows:\n1.Select the OPEN button\n2.Select the file you want to play\n3.Click the PLAY button TWICE to play the first file\n4.AFTER a file is imported,you just have to click the PLAY button ONCE\n    while the execution of the programme\n5.Use the Windows volume controller to control volume\n\n\nCopyright 2014, Agarwal solutions Enterprise,All Rights Reserved\n                                     Click OK to continue","User Instruction Manual",JOptionPane.INFORMATION_MESSAGE);
        String userName=JOptionPane.showInputDialog(null,"Hey user,please enter your name","SUYASH AGARWAL");
        Toolkit.getDefaultToolkit().beep();
        JOptionPane.showMessageDialog(null,"Make sure your SPEAKERS are CONNECTED.","Output Scanner",JOptionPane.WARNING_MESSAGE);
        new BrainFreak_Media_Player();
        int response=JOptionPane.showConfirmDialog(null,"Do you want to know about the creator of this programme?");
        if(response==0)
        {
            JOptionPane.showMessageDialog(null,"The creator of this Media Player is SUYASH AGARWAL of class Xth,\nHe is an average school boy who loves programming","The Creator",JOptionPane.INFORMATION_MESSAGE);
            JOptionPane.showMessageDialog(null,"The programme that you are going to see is a GENUINE INTERFACE,please DONOT copy","A Message from The Creator",JOptionPane.INFORMATION_MESSAGE);
        }
        String users="Users.txt";
        try
        {
            userName+=" , ";
            FileWriter ob=new FileWriter(users,true);
            if(userName.equalsIgnoreCase("SUYASH AGARWAL , "))
            {
            }
            else
            {
                ob.append(userName);
                ob.append("\n");
                ob.close();
            }
        }
        catch(Exception e)
        {
            System.out.println();
        }
    }
    public void actionPerformed(ActionEvent e)//allots the action to be proceeded when a particular button is clicked
    {    
        Object select=e.getSource();
        if(select==play)
        {
            playAudio();
            System.out.println("\f");
            System.out.println("PLAYLIST OF YOUR SONGS:");
            for(int a=0;a<i;a++)
            {
                System.out.println((a+1)+"."+songs[a+1]);
            }
            if(r==2)
            {
                System.out.println("\nNow Playing: "+songs[j]);
                play.setEnabled(false);
            }
            else
                r++;
            stop.setEnabled(true);
            if(plays==0)
                prev.setEnabled(false);
            else if(nxt>1)
                prev.setEnabled(true);
            plays++;
        }
        else if(select==stop)
        {
            stopPlayback=true;
            System.out.println("\f");
            System.out.println("PLAYLIST OF YOUR SONGS:");
            for(int a=0;a<i;a++)
            {
                System.out.println((a+1)+"."+songs[a+1]);
            }
            play.setEnabled(true);
        }
        else if(select==next)
        {
            stopPlayback=true;
            j++;
            if(j==nxt)
            {
                next.setEnabled(false);
            }
            but.setIcon(image[j]);
            r=2;
            System.out.println("\f");
            System.out.println("PLAYLIST OF YOUR SONGS:");
            for(int a=0;a<i;a++)
            {
                System.out.println((a+1)+"."+songs[a+1]);
            }
            play.setEnabled(true);
        }
        else if(select==prev)
        {            
            stopPlayback=true;
            next.setEnabled(true);
            j--;
            play.setEnabled(true);
            if(j==1)
            {
                prev.setEnabled(false);
                plays=0;
            }
            but.setIcon(image[j]);
        }
        else if(select==open)
        {
            stopPlayback=true;
            files.showOpenDialog(null);
            int ret=0;
            if(ret==JFileChooser.APPROVE_OPTION)
            {
                i++;
                j++;
                fileArray[i]=files.getSelectedFile();
                nxt++;
                play.setEnabled(true);
                nowPlaying=fileArray[i].getName();
                songs[i]=nowPlaying;
                System.out.println("\f");
                System.out.println("PLAYLIST OF YOUR SONGS:");
                for(int a=0;a<i;a++)
                {
                    System.out.println((a+1)+"."+songs[a+1]);
                }
                if(check==0)
                    r=1;
                else
                    check++;
            }
        }
    }

    private void playAudio()//extracts the information of the song
    {
        try
        {
            soundFile=fileArray[j];
            audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            audioFormat = audioInputStream.getFormat();
            DataLine.Info dataLineInfo =new DataLine.Info(SourceDataLine.class,audioFormat);
            sourceDataLine =(SourceDataLine)AudioSystem.getLine(dataLineInfo);
            if(plays==1&&r==2||plays>1&&r==2||plays>1)
            {
                Toolkit.getDefaultToolkit().beep();
            }
            new PlayThread().start();
        }
        catch (Exception e) 
        {
            int ret=0;
            j--;
            i--;
            r=-1;
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,"      There was a problem in loading your file.\nThis media player supports only .au, .wav, .waf files.\n                     Click OK to continue",
                "File Manager",JOptionPane.ERROR_MESSAGE);
            System.out.println("\f");
        }
    }

    class PlayThread extends Thread//feeds the song in binary to the speakers
    {
        byte temp[] = new byte[100];
        public void run(){
            try{
                sourceDataLine.open(audioFormat);
                sourceDataLine.start();
                int cnt;
                while((cnt = audioInputStream.read(temp,0,temp.length))!= -1&& stopPlayback == false)
                {
                    if(cnt > 0)
                    {
                        sourceDataLine.write(temp,0, cnt);
                    }
                }
                sourceDataLine.drain();
                sourceDataLine.close();
                stop.setEnabled(false);
                stopPlayback = false;
                play.setEnabled(true);
            }catch (Exception e) 
            {
                System.out.println(e);
            }
        }
    }
}