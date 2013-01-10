package imageflow;

/**
 * About this Code
 *
 * The original code is from Romain Guy's example "A Music Shelf in Java2D".
 * It can be found here:
 *
 *   http://www.curious-creature.org/2005/07/09/a-music-shelf-in-java2d/
 *
 * Updated Code
 * This code has been updated by Kevin Long (codebeach.com) to make it more
 * generic and more component like.
 *
 * History:
 *
 * 2/17/2008
 * ---------
 * - Removed hard coded strings for labels and images
 * - Removed requirement for images to be included in the jar
 * - Removed CD case drawing
 * - Support for non-square images
 * - Support for loading images from thumbnails
 * - External methods to set and get currently selected item
 * - Added support for ListSelectionListener
 */

import java.awt.HeadlessException;

import javax.swing.*;
import java.io.File;
import java.awt.*;

public class MainTest extends JFrame
{

	private static final long serialVersionUID = 1L;
    private ImageFlow imageFlow = null;

    public MainTest() throws HeadlessException {
        super("Image Flow");

        buildContentPane();

        setSize(640, 360);
        setResizable(true);
        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void buildContentPane() {


        JPanel imageFlowPanel = new JPanel(new StackLayout());
        imageFlowPanel.add(new GradientPanel(), StackLayout.BOTTOM);

        try
        {
            imageFlow = new ImageFlow(new File("c:/projects/components/images/"));
            imageFlowPanel.add(imageFlow,  StackLayout.TOP);
        }
        catch (Exception e)
        {

        }

        getContentPane().setLayout(new BorderLayout());

        getContentPane().add(imageFlowPanel, BorderLayout.CENTER);
    }
}
