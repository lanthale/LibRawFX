/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.librawimageio;

import java.awt.Dimension;
import javax.imageio.ImageReadParam;

/**
 *
 * @author selfemp
 */
public class RAWImageIOImageReadParam extends ImageReadParam {

    public RAWImageIOImageReadParam() {
    }

    @Override
    public void setSourceRenderSize(Dimension size) throws UnsupportedOperationException {
        super.setSourceRenderSize(size); 
    }

    @Override
    public boolean canSetSourceRenderSize() {
        return true;
    }
    
    
    
}
