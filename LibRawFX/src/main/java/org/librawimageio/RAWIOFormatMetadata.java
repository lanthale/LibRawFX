/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.librawimageio;

import org.w3c.dom.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.imageio.metadata.IIOInvalidTreeException;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.metadata.IIOMetadataFormat;
import javax.imageio.metadata.IIOMetadataNode;

/**
 *
 * @author selfemp
 */
public class RAWIOFormatMetadata extends IIOMetadata {

    static final boolean standardMetadataFormatSupported = false;
    static final String nativeMetadataFormatName
            = "org.librawfx.RAWIOMetadataFormat_1.0";
    static final String nativeMetadataFormatClassName
            = "org.librawfx.RAWIOMetadataFormat";
    static final String[] extraMetadataFormatNames = null;
    static final String[] extraMetadataFormatClassNames = null;

    // Keyword/value pairs
    List keywords = new ArrayList();
    List values = new ArrayList();

    public RAWIOFormatMetadata() {
        super(standardMetadataFormatSupported,
                nativeMetadataFormatName,
                nativeMetadataFormatClassName,
                extraMetadataFormatNames,
                extraMetadataFormatClassNames);
    }

    public IIOMetadataFormat getMetadataFormat(String formatName) {
        if (!formatName.equals(nativeMetadataFormatName)) {
            throw new IllegalArgumentException("Bad format name!");
        }
        return RAWIOMetadataFormat.getDefaultInstance();
    }

    @Override
    public boolean isReadOnly() {
        return true;
    }

    @Override
    public Node getAsTree(String formatName) {
        if (!formatName.equals(nativeMetadataFormatName)) {
            throw new IllegalArgumentException("Bad format name!");
        }

        // Create a root node
        IIOMetadataNode root
                = new IIOMetadataNode(nativeMetadataFormatName);

        // Add a child to the root node for each keyword/value pair
        Iterator keywordIter = keywords.iterator();
        Iterator valueIter = values.iterator();
        while (keywordIter.hasNext()) {
            IIOMetadataNode node
                    = new IIOMetadataNode("KeywordValuePair");
            node.setAttribute("keyword", (String) keywordIter.next());
            node.setAttribute("value", (String) valueIter.next());
            root.appendChild(node);
        }

        return root;
    }

    @Override
    public void mergeTree(String formatName, Node root) throws IIOInvalidTreeException {
        if (!formatName.equals(nativeMetadataFormatName)) {
            throw new IllegalArgumentException("Bad format name!");
        }

        Node node = root;
        if (!node.getNodeName().equals(nativeMetadataFormatName)) {
            fatal(node, "Root must be " + nativeMetadataFormatName);
        }
        node = node.getFirstChild();
        while (node != null) {
            if (!node.getNodeName().equals("KeywordValuePair")) {
                fatal(node, "Node name not KeywordValuePair!");
            }
            NamedNodeMap attributes = node.getAttributes();
            Node keywordNode = attributes.getNamedItem("keyword");
            Node valueNode = attributes.getNamedItem("value");
            if (keywordNode == null || valueNode == null) {
                fatal(node, "Keyword or value missing!");
            }

            // Store keyword and value
            keywords.add((String) keywordNode.getNodeValue());
            values.add((String) valueNode.getNodeValue());

            // Move to the next sibling
            node = node.getNextSibling();
        }
    }

    private void fatal(Node node, String reason)
            throws IIOInvalidTreeException {
        throw new IIOInvalidTreeException(reason, node);
    }

    @Override
    public void reset() {
        this.keywords = new ArrayList();
        this.values = new ArrayList();
    }

}
