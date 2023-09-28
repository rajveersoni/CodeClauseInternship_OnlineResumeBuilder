package com.example;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class
App {
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
            public void run()
            {
                Model resumemodel = new Model();
                ResumeBuilder viewers = new ResumeBuilder();
                ResumeController controller = new ResumeController(resumemodel, viewers);
                viewers.setVisible(true);
            }
        });
    }
}

class Model
{
    private String name;
    private String email;
    private String education;
    private String linkedIn;
    private String skills;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLinkedIn() {
        return linkedIn;
    }
    public void setLinkedIn(String linkedIn)
    {
        this.linkedIn = linkedIn;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEducation() {
        return education;
    }
    public void setEducation(String education) {
        this.education = education;
    }
    public String getSkills() {
        return skills;
    }
    public void setSkills(String skills) {
        this.skills = skills;
    }
}

class ResumeBuilder extends JFrame
{
    private JTextField nameField;
    private JTextField linkedinField;
    private JTextField emailField;
    private JTextArea educationArea;
    private JTextField skillsArea;

    public ResumeBuilder()
    {
        setTitle("Online Resume Builder");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        initComponents();
        pack();
        setLocationRelativeTo(null);
    }

    private void initComponents()
    {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);

        JLabel fullname = new JLabel("Name:");
        add(fullname, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        nameField = new JTextField(15);
        add(nameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        JLabel linkedin = new JLabel("LinkedIn profile ID:");
        add(linkedin, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        linkedinField= new JTextField(20);
        add(linkedinField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        JLabel emailid = new JLabel("Email:");
        add(emailid, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        emailField = new JTextField(20);
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        JLabel education = new JLabel("Education Details:");
        add(education, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridheight = 3;
        educationArea = new JTextArea(5, 20);
        add(educationArea, gbc);

        gbc.gridx = 1;
        gbc.gridy = 20;
        gbc.gridheight = 1;
        gbc.fill = GridBagConstraints.NONE;

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.fill = GridBagConstraints.NONE;
        JLabel skills = new JLabel("Skills: ");
        add(skills, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridheight = 9;
        skillsArea = new JTextField(20);
        add(skillsArea, gbc);

        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        //Button creation to generate PDF file
        JButton generatePDFButton = new JButton("GENERATE RESUME");
        add(generatePDFButton, gbc);

        generatePDFButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePDF();
            }
        });
    }

    private void generatePDF()
    {
        //generation of resume as pdf file
        Model model = new Model();
        model.setName(nameField.getText());
        model.setLinkedIn(linkedinField.getText());
        model.setEmail(emailField.getText());
        model.setEducation(educationArea.getText());
        model.setSkills(skillsArea.getText());

        ResumePDFGenerator pdfGenerator = new ResumePDFGenerator();
        pdfGenerator.generatePDF(model);
        JOptionPane.showMessageDialog(this, "Resume generated successfully!!");
    }
}

class ResumeController
{
    private Model resumemodel;
    private ResumeBuilder viewres;

    public ResumeController(Model resumemodel, ResumeBuilder viewres)
    {
        this.resumemodel = resumemodel;
        this.viewres = viewres;
    }
}

class ResumePDFGenerator {
    public void generatePDF(Model model) {
        try
        {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream("resume.pdf"));
            document.open();
            //create font objects
            Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
            Font nameFont = new Font(Font.FontFamily.TIMES_ROMAN,22, Font.BOLD);
            Font font = new Font(Font.FontFamily.TIMES_ROMAN, 16);
            // Add resume details to the PDF
            document.addTitle("RESUME");
            Paragraph name=new Paragraph(model.getName(),nameFont);
            name.setAlignment(Element.ALIGN_CENTER);
            document.add(name);
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("LinkedIn ID: ",boldFont));
            document.add(new Paragraph(model.getLinkedIn(),font));
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Mail ID: ",boldFont));
            document.add(new Paragraph(model.getEmail(),font));
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Education Details: ",boldFont));
            document.add(new Paragraph(model.getEducation(),font));
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph("Skills: ",boldFont));
            document.add(new Paragraph(model.getSkills(),font));
            document.add(Chunk.NEWLINE);
            document.close();
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
