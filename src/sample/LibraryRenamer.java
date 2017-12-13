package sample;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sun.javafx.css.Stylesheet;
import com.sun.javafx.css.parser.CSSParser;
import org.apache.commons.io.FilenameUtils;

public class LibraryRenamer {
    private JTextField directoryPathTextField;
    private JComboBox<String> functionSelector;
    private JButton submitButton;
    private StyledButtonUI buttonUI = new StyledButtonUI();
    public JPanel mainPanel;
    private static final String[] SORT_CHOICES = {"Rename Folders Using Largest File", "Rename Inner Files Using Folder Name"};

    public LibraryRenamer() {
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = functionSelector.getSelectedItem().toString();
                String directory = directoryPathTextField.getText();

                switch (selectedOption) {
                    case "Rename Folders Using Largest File":
                        renameFolderUsingLargestFile(directory);
                        break;
                    case "Rename Inner Files Using Folder Name":
                        renameUsingFolderName(directory);

                        break;
                }

            }
        });

    }

    /**
     * Renames the largest file in all of the child directories using the child directories name
     * @param dir The path of the Parent/ Container Directory of which all of the child directories are located
     */
    private void renameUsingFolderName(String dir) {

        // so we're trying to rename files to the name of their parent directory
        // But somehow we're having an OutOfBoundsException
        File f = new File(dir);
        ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
        if (f.isDirectory()) {
            for (int i = 0; i < files.size(); i++) {
                String path = files.get(i).getAbsolutePath();

                // We create new paths for a new file so we can grab the new name to name the movie file
                File scenario = new File(path);
                String name = scenario.getName();

                long greatestSize = 0;
                int place = -1;
                if (scenario.isDirectory() && scenario.listFiles() != null) {
                    List<File> childDir = new ArrayList<File>(Arrays.asList(scenario.listFiles()));
                    for (int k = 0; k < childDir.size(); k++) {
                        if (childDir.size() != 0) {
                            if (greatestSize < childDir.get(k).length()) {
                                greatestSize = childDir.get(k).length();
                                place = k;
                            }
                        }
                    }
                    if (place > -1) {
                        File movieFile = new File(childDir.get(place).getAbsolutePath());
                        String ext1 = FilenameUtils.getExtension(childDir.get(place).getAbsolutePath());
                        File newFile = new File(path + "\\" + name + "." + ext1);
                        movieFile.renameTo(newFile);
                    }

                }

            }

        }

    }

    /**
     * Renames all the child directories in a given directory using the largest filename of the largest file in said directory
     * @param dir The Directory path of the parent directory where we want to rename files
     */
    private void renameFolderUsingLargestFile(String dir) {
        File f = new File(dir);
        ArrayList<File> files = new ArrayList<File>(Arrays.asList(f.listFiles()));
        if (f.isDirectory()) {
            for (int i = 0; i < files.size(); i++) {
                String path = files.get(i).getAbsolutePath();
                // We create new paths for a new file so we can grab the new name to name the movie file
                File scenario = new File(path);
                long greatestSize = 0;
                int place = -1;
                if (scenario.isDirectory() && scenario.listFiles() != null) {
                    List<File> childDir = new ArrayList<File>(Arrays.asList(scenario.listFiles()));
                    for (int k = 0; k < childDir.size(); k++) {
                        if (childDir.size() != 0) {
                            if (greatestSize < childDir.get(k).length()) {
                                greatestSize = childDir.get(k).length();
                                place = k;
                            }
                        }
                    }
                    if (place > -1) {
                        File movieFile = new File(childDir.get(place).getAbsolutePath());
                        String largestFileName = FilenameUtils.removeExtension(movieFile.getName());
                        File newDirFile = new File(f.getAbsolutePath() + "\\" + largestFileName);
                        scenario.renameTo(newDirFile);
                    }

                }

            }

        }
    }

    private void createUIComponents() {
        submitButton = new JButton("Submit");
        buttonUI = new StyledButtonUI();
        buttonUI.installUI(submitButton);
    }
}
