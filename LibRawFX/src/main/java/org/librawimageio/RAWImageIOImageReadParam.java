package org.librawimageio;

import java.awt.Dimension;
import javax.imageio.ImageReadParam;
import org.librawdecodersettings.RawDecoderSettings;

public class RAWImageIOImageReadParam extends ImageReadParam {

    private RawDecoderSettings settings;   // null = statische Default-Settings

    private boolean preserveAspectRatio;

    public RAWImageIOImageReadParam() {
        preserveAspectRatio=true;
    }

    public RawDecoderSettings getSettings() {
        return settings;
    }

    public void setSettings(RawDecoderSettings settings) {
        this.settings = settings;
    }

    public boolean isPreserveAspectRatio() {
        return preserveAspectRatio;
    }

    public void setPreserveAspectRatio(boolean b) {
        this.preserveAspectRatio = b;
    }

    @Override
    public boolean canSetSourceRenderSize() {
        return true;
    }

    @Override
    public void setSourceRenderSize(Dimension size) {
        super.setSourceRenderSize(size);
    }
}
