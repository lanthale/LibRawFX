/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.librawimageio;

/**
 *
 * @author cleme
 */
public class ProviderInfo {
    // TODO: Consider reading the META-INF/MANIFEST.MF from the class path using java.util.jar.Manifest.
    // Use the manifest that is located in the same class path folder as the package.

    private final String title;
    private final String vendorName;
    private final String version;

    /**
     * Creates a provider information instance based on the given package.
     *
     * @param pPackage the package to get provider information from. This should
     * typically be the package containing the Spi class.
     *
     * @throws IllegalArgumentException if {@code pPackage == null}
     */
    public ProviderInfo(final Package pPackage) {

        String title = pPackage.getImplementationTitle();
        this.title = title != null ? title : pPackage.getName();

        String vendor = pPackage.getImplementationVendor();
        vendorName = vendor != null ? vendor : fakeVendor(pPackage);

        String version = pPackage.getImplementationVersion();
        this.version = version != null ? version : fakeVersion(pPackage);
    }

    private static String fakeVendor(final Package pPackage) {
        String name = pPackage.getName();
        return name.startsWith("com.twelvemonkeys") ? "TwelveMonkeys" : name;
    }

    private String fakeVersion(final Package pPackage) {
        String name = pPackage.getName();
        return name.startsWith("com.twelvemonkeys") ? "DEV" : "Unspecified";
    }

    /**
     * Returns the implementation title, as specified in the manifest entry
     * {@code Implementation-Title} for the package. If the title is
     * unavailable, the package name or some default name for known packages are
     * used.
     *
     * @return the implementation title
     */
    final String getImplementationTitle() {
        return title;
    }

    /**
     * Returns the vendor name, as specified in the manifest entry
     * {@code Implementation-Vendor} for the package. If the vendor name is
     * unavailable, the package name or some default name for known packages are
     * used.
     *
     * @return the vendor name.
     */
    public final String getVendorName() {
        return vendorName;
    }

    /**
     * Returns the version/build number string, as specified in the manifest
     * entry {@code Implementation-Version} for the package. If the version is
     * unavailable, some arbitrary (non-{@code null}) value is used.
     *
     * @return the vendor name.
     */
    public final String getVersion() {
        return version;
    }

    @Override
    public String toString() {
        return title + ", " + version + " by " + vendorName;
    }
}
