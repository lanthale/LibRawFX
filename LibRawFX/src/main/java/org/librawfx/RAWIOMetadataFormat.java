/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.librawfx;

import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadataFormatImpl;

/**
 *
 * @author selfemp
 */
public class RAWIOMetadataFormat extends IIOMetadataFormatImpl {

    // Create a single instance of this class (singleton pattern)
    private static RAWIOMetadataFormat defaultInstance
            = new RAWIOMetadataFormat();

    // Make constructor private to enforce the singleton pattern
    private RAWIOMetadataFormat() {
        // Set the name of the root node
        // The root node has a single child node type that may repeat
        super("com.mycompany.imageio.MyFormatMetadata_1.0",
                CHILD_POLICY_REPEAT);

        // Set up the "KeywordValuePair" node, which has no children
        addElement("KeywordValuePair",
                "com.mycompany.imageio.MyFormatMetadata_1.0",
                CHILD_POLICY_EMPTY);

        // Set up attribute "keyword" which is a String that is required
        // and has no default value
        addAttribute("KeywordValuePair", "keyword", DATATYPE_STRING,
                true, null);
        // Set up attribute "value" which is a String that is required
        // and has no default value
        addAttribute("KeywordValuePair", "value", DATATYPE_STRING,
                true, null);
    }

    @Override
    public boolean canNodeAppear(String elementName, ImageTypeSpecifier imageType) {
        return elementName.equals("KeywordValuePair");
    }

    // Return the singleton instance
    public static RAWIOMetadataFormat getDefaultInstance() {
        return defaultInstance;
    }

}
