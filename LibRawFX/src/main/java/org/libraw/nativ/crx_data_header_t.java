// Generated by jextract

package org.libraw.nativ;

import java.lang.invoke.*;
import java.lang.foreign.*;
import java.nio.ByteOrder;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.lang.foreign.ValueLayout.*;
import static java.lang.foreign.MemoryLayout.PathElement.*;

/**
 * {@snippet lang=c :
 * struct {
 *     int32_t version;
 *     int32_t f_width;
 *     int32_t f_height;
 *     int32_t tileWidth;
 *     int32_t tileHeight;
 *     int32_t nBits;
 *     int32_t nPlanes;
 *     int32_t cfaLayout;
 *     int32_t encType;
 *     int32_t imageLevels;
 *     int32_t hasTileCols;
 *     int32_t hasTileRows;
 *     int32_t mdatHdrSize;
 *     int32_t medianBits;
 *     uint32_t MediaSize;
 *     INT64 MediaOffset;
 *     uint32_t MediaType;
 *     crx_sample_to_chunk_t *stsc_data;
 *     uint32_t stsc_count;
 *     uint32_t sample_count;
 *     uint32_t sample_size;
 *     int32_t *sample_sizes;
 *     uint32_t chunk_count;
 *     INT64 *chunk_offsets;
 * }
 * }
 */
public class crx_data_header_t {

    crx_data_header_t() {
        // Should not be called directly
    }

    private static final GroupLayout $LAYOUT = MemoryLayout.structLayout(
        libraw_h.C_INT.withName("version"),
        libraw_h.C_INT.withName("f_width"),
        libraw_h.C_INT.withName("f_height"),
        libraw_h.C_INT.withName("tileWidth"),
        libraw_h.C_INT.withName("tileHeight"),
        libraw_h.C_INT.withName("nBits"),
        libraw_h.C_INT.withName("nPlanes"),
        libraw_h.C_INT.withName("cfaLayout"),
        libraw_h.C_INT.withName("encType"),
        libraw_h.C_INT.withName("imageLevels"),
        libraw_h.C_INT.withName("hasTileCols"),
        libraw_h.C_INT.withName("hasTileRows"),
        libraw_h.C_INT.withName("mdatHdrSize"),
        libraw_h.C_INT.withName("medianBits"),
        libraw_h.C_INT.withName("MediaSize"),
        MemoryLayout.paddingLayout(4),
        libraw_h.C_LONG_LONG.withName("MediaOffset"),
        libraw_h.C_INT.withName("MediaType"),
        MemoryLayout.paddingLayout(4),
        libraw_h.C_POINTER.withName("stsc_data"),
        libraw_h.C_INT.withName("stsc_count"),
        libraw_h.C_INT.withName("sample_count"),
        libraw_h.C_INT.withName("sample_size"),
        MemoryLayout.paddingLayout(4),
        libraw_h.C_POINTER.withName("sample_sizes"),
        libraw_h.C_INT.withName("chunk_count"),
        MemoryLayout.paddingLayout(4),
        libraw_h.C_POINTER.withName("chunk_offsets")
    ).withName("$anon$124:9");

    /**
     * The layout of this struct
     */
    public static final GroupLayout layout() {
        return $LAYOUT;
    }

    private static final OfInt version$LAYOUT = (OfInt)$LAYOUT.select(groupElement("version"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int32_t version
     * }
     */
    public static final OfInt version$layout() {
        return version$LAYOUT;
    }

    private static final long version$OFFSET = 0;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int32_t version
     * }
     */
    public static final long version$offset() {
        return version$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int32_t version
     * }
     */
    public static int version(MemorySegment struct) {
        return struct.get(version$LAYOUT, version$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int32_t version
     * }
     */
    public static void version(MemorySegment struct, int fieldValue) {
        struct.set(version$LAYOUT, version$OFFSET, fieldValue);
    }

    private static final OfInt f_width$LAYOUT = (OfInt)$LAYOUT.select(groupElement("f_width"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int32_t f_width
     * }
     */
    public static final OfInt f_width$layout() {
        return f_width$LAYOUT;
    }

    private static final long f_width$OFFSET = 4;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int32_t f_width
     * }
     */
    public static final long f_width$offset() {
        return f_width$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int32_t f_width
     * }
     */
    public static int f_width(MemorySegment struct) {
        return struct.get(f_width$LAYOUT, f_width$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int32_t f_width
     * }
     */
    public static void f_width(MemorySegment struct, int fieldValue) {
        struct.set(f_width$LAYOUT, f_width$OFFSET, fieldValue);
    }

    private static final OfInt f_height$LAYOUT = (OfInt)$LAYOUT.select(groupElement("f_height"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int32_t f_height
     * }
     */
    public static final OfInt f_height$layout() {
        return f_height$LAYOUT;
    }

    private static final long f_height$OFFSET = 8;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int32_t f_height
     * }
     */
    public static final long f_height$offset() {
        return f_height$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int32_t f_height
     * }
     */
    public static int f_height(MemorySegment struct) {
        return struct.get(f_height$LAYOUT, f_height$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int32_t f_height
     * }
     */
    public static void f_height(MemorySegment struct, int fieldValue) {
        struct.set(f_height$LAYOUT, f_height$OFFSET, fieldValue);
    }

    private static final OfInt tileWidth$LAYOUT = (OfInt)$LAYOUT.select(groupElement("tileWidth"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int32_t tileWidth
     * }
     */
    public static final OfInt tileWidth$layout() {
        return tileWidth$LAYOUT;
    }

    private static final long tileWidth$OFFSET = 12;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int32_t tileWidth
     * }
     */
    public static final long tileWidth$offset() {
        return tileWidth$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int32_t tileWidth
     * }
     */
    public static int tileWidth(MemorySegment struct) {
        return struct.get(tileWidth$LAYOUT, tileWidth$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int32_t tileWidth
     * }
     */
    public static void tileWidth(MemorySegment struct, int fieldValue) {
        struct.set(tileWidth$LAYOUT, tileWidth$OFFSET, fieldValue);
    }

    private static final OfInt tileHeight$LAYOUT = (OfInt)$LAYOUT.select(groupElement("tileHeight"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int32_t tileHeight
     * }
     */
    public static final OfInt tileHeight$layout() {
        return tileHeight$LAYOUT;
    }

    private static final long tileHeight$OFFSET = 16;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int32_t tileHeight
     * }
     */
    public static final long tileHeight$offset() {
        return tileHeight$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int32_t tileHeight
     * }
     */
    public static int tileHeight(MemorySegment struct) {
        return struct.get(tileHeight$LAYOUT, tileHeight$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int32_t tileHeight
     * }
     */
    public static void tileHeight(MemorySegment struct, int fieldValue) {
        struct.set(tileHeight$LAYOUT, tileHeight$OFFSET, fieldValue);
    }

    private static final OfInt nBits$LAYOUT = (OfInt)$LAYOUT.select(groupElement("nBits"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int32_t nBits
     * }
     */
    public static final OfInt nBits$layout() {
        return nBits$LAYOUT;
    }

    private static final long nBits$OFFSET = 20;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int32_t nBits
     * }
     */
    public static final long nBits$offset() {
        return nBits$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int32_t nBits
     * }
     */
    public static int nBits(MemorySegment struct) {
        return struct.get(nBits$LAYOUT, nBits$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int32_t nBits
     * }
     */
    public static void nBits(MemorySegment struct, int fieldValue) {
        struct.set(nBits$LAYOUT, nBits$OFFSET, fieldValue);
    }

    private static final OfInt nPlanes$LAYOUT = (OfInt)$LAYOUT.select(groupElement("nPlanes"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int32_t nPlanes
     * }
     */
    public static final OfInt nPlanes$layout() {
        return nPlanes$LAYOUT;
    }

    private static final long nPlanes$OFFSET = 24;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int32_t nPlanes
     * }
     */
    public static final long nPlanes$offset() {
        return nPlanes$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int32_t nPlanes
     * }
     */
    public static int nPlanes(MemorySegment struct) {
        return struct.get(nPlanes$LAYOUT, nPlanes$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int32_t nPlanes
     * }
     */
    public static void nPlanes(MemorySegment struct, int fieldValue) {
        struct.set(nPlanes$LAYOUT, nPlanes$OFFSET, fieldValue);
    }

    private static final OfInt cfaLayout$LAYOUT = (OfInt)$LAYOUT.select(groupElement("cfaLayout"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int32_t cfaLayout
     * }
     */
    public static final OfInt cfaLayout$layout() {
        return cfaLayout$LAYOUT;
    }

    private static final long cfaLayout$OFFSET = 28;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int32_t cfaLayout
     * }
     */
    public static final long cfaLayout$offset() {
        return cfaLayout$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int32_t cfaLayout
     * }
     */
    public static int cfaLayout(MemorySegment struct) {
        return struct.get(cfaLayout$LAYOUT, cfaLayout$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int32_t cfaLayout
     * }
     */
    public static void cfaLayout(MemorySegment struct, int fieldValue) {
        struct.set(cfaLayout$LAYOUT, cfaLayout$OFFSET, fieldValue);
    }

    private static final OfInt encType$LAYOUT = (OfInt)$LAYOUT.select(groupElement("encType"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int32_t encType
     * }
     */
    public static final OfInt encType$layout() {
        return encType$LAYOUT;
    }

    private static final long encType$OFFSET = 32;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int32_t encType
     * }
     */
    public static final long encType$offset() {
        return encType$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int32_t encType
     * }
     */
    public static int encType(MemorySegment struct) {
        return struct.get(encType$LAYOUT, encType$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int32_t encType
     * }
     */
    public static void encType(MemorySegment struct, int fieldValue) {
        struct.set(encType$LAYOUT, encType$OFFSET, fieldValue);
    }

    private static final OfInt imageLevels$LAYOUT = (OfInt)$LAYOUT.select(groupElement("imageLevels"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int32_t imageLevels
     * }
     */
    public static final OfInt imageLevels$layout() {
        return imageLevels$LAYOUT;
    }

    private static final long imageLevels$OFFSET = 36;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int32_t imageLevels
     * }
     */
    public static final long imageLevels$offset() {
        return imageLevels$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int32_t imageLevels
     * }
     */
    public static int imageLevels(MemorySegment struct) {
        return struct.get(imageLevels$LAYOUT, imageLevels$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int32_t imageLevels
     * }
     */
    public static void imageLevels(MemorySegment struct, int fieldValue) {
        struct.set(imageLevels$LAYOUT, imageLevels$OFFSET, fieldValue);
    }

    private static final OfInt hasTileCols$LAYOUT = (OfInt)$LAYOUT.select(groupElement("hasTileCols"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int32_t hasTileCols
     * }
     */
    public static final OfInt hasTileCols$layout() {
        return hasTileCols$LAYOUT;
    }

    private static final long hasTileCols$OFFSET = 40;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int32_t hasTileCols
     * }
     */
    public static final long hasTileCols$offset() {
        return hasTileCols$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int32_t hasTileCols
     * }
     */
    public static int hasTileCols(MemorySegment struct) {
        return struct.get(hasTileCols$LAYOUT, hasTileCols$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int32_t hasTileCols
     * }
     */
    public static void hasTileCols(MemorySegment struct, int fieldValue) {
        struct.set(hasTileCols$LAYOUT, hasTileCols$OFFSET, fieldValue);
    }

    private static final OfInt hasTileRows$LAYOUT = (OfInt)$LAYOUT.select(groupElement("hasTileRows"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int32_t hasTileRows
     * }
     */
    public static final OfInt hasTileRows$layout() {
        return hasTileRows$LAYOUT;
    }

    private static final long hasTileRows$OFFSET = 44;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int32_t hasTileRows
     * }
     */
    public static final long hasTileRows$offset() {
        return hasTileRows$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int32_t hasTileRows
     * }
     */
    public static int hasTileRows(MemorySegment struct) {
        return struct.get(hasTileRows$LAYOUT, hasTileRows$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int32_t hasTileRows
     * }
     */
    public static void hasTileRows(MemorySegment struct, int fieldValue) {
        struct.set(hasTileRows$LAYOUT, hasTileRows$OFFSET, fieldValue);
    }

    private static final OfInt mdatHdrSize$LAYOUT = (OfInt)$LAYOUT.select(groupElement("mdatHdrSize"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int32_t mdatHdrSize
     * }
     */
    public static final OfInt mdatHdrSize$layout() {
        return mdatHdrSize$LAYOUT;
    }

    private static final long mdatHdrSize$OFFSET = 48;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int32_t mdatHdrSize
     * }
     */
    public static final long mdatHdrSize$offset() {
        return mdatHdrSize$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int32_t mdatHdrSize
     * }
     */
    public static int mdatHdrSize(MemorySegment struct) {
        return struct.get(mdatHdrSize$LAYOUT, mdatHdrSize$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int32_t mdatHdrSize
     * }
     */
    public static void mdatHdrSize(MemorySegment struct, int fieldValue) {
        struct.set(mdatHdrSize$LAYOUT, mdatHdrSize$OFFSET, fieldValue);
    }

    private static final OfInt medianBits$LAYOUT = (OfInt)$LAYOUT.select(groupElement("medianBits"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int32_t medianBits
     * }
     */
    public static final OfInt medianBits$layout() {
        return medianBits$LAYOUT;
    }

    private static final long medianBits$OFFSET = 52;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int32_t medianBits
     * }
     */
    public static final long medianBits$offset() {
        return medianBits$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int32_t medianBits
     * }
     */
    public static int medianBits(MemorySegment struct) {
        return struct.get(medianBits$LAYOUT, medianBits$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int32_t medianBits
     * }
     */
    public static void medianBits(MemorySegment struct, int fieldValue) {
        struct.set(medianBits$LAYOUT, medianBits$OFFSET, fieldValue);
    }

    private static final OfInt MediaSize$LAYOUT = (OfInt)$LAYOUT.select(groupElement("MediaSize"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * uint32_t MediaSize
     * }
     */
    public static final OfInt MediaSize$layout() {
        return MediaSize$LAYOUT;
    }

    private static final long MediaSize$OFFSET = 56;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * uint32_t MediaSize
     * }
     */
    public static final long MediaSize$offset() {
        return MediaSize$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * uint32_t MediaSize
     * }
     */
    public static int MediaSize(MemorySegment struct) {
        return struct.get(MediaSize$LAYOUT, MediaSize$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * uint32_t MediaSize
     * }
     */
    public static void MediaSize(MemorySegment struct, int fieldValue) {
        struct.set(MediaSize$LAYOUT, MediaSize$OFFSET, fieldValue);
    }

    private static final OfLong MediaOffset$LAYOUT = (OfLong)$LAYOUT.select(groupElement("MediaOffset"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * INT64 MediaOffset
     * }
     */
    public static final OfLong MediaOffset$layout() {
        return MediaOffset$LAYOUT;
    }

    private static final long MediaOffset$OFFSET = 64;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * INT64 MediaOffset
     * }
     */
    public static final long MediaOffset$offset() {
        return MediaOffset$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * INT64 MediaOffset
     * }
     */
    public static long MediaOffset(MemorySegment struct) {
        return struct.get(MediaOffset$LAYOUT, MediaOffset$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * INT64 MediaOffset
     * }
     */
    public static void MediaOffset(MemorySegment struct, long fieldValue) {
        struct.set(MediaOffset$LAYOUT, MediaOffset$OFFSET, fieldValue);
    }

    private static final OfInt MediaType$LAYOUT = (OfInt)$LAYOUT.select(groupElement("MediaType"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * uint32_t MediaType
     * }
     */
    public static final OfInt MediaType$layout() {
        return MediaType$LAYOUT;
    }

    private static final long MediaType$OFFSET = 72;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * uint32_t MediaType
     * }
     */
    public static final long MediaType$offset() {
        return MediaType$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * uint32_t MediaType
     * }
     */
    public static int MediaType(MemorySegment struct) {
        return struct.get(MediaType$LAYOUT, MediaType$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * uint32_t MediaType
     * }
     */
    public static void MediaType(MemorySegment struct, int fieldValue) {
        struct.set(MediaType$LAYOUT, MediaType$OFFSET, fieldValue);
    }

    private static final AddressLayout stsc_data$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("stsc_data"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * crx_sample_to_chunk_t *stsc_data
     * }
     */
    public static final AddressLayout stsc_data$layout() {
        return stsc_data$LAYOUT;
    }

    private static final long stsc_data$OFFSET = 80;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * crx_sample_to_chunk_t *stsc_data
     * }
     */
    public static final long stsc_data$offset() {
        return stsc_data$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * crx_sample_to_chunk_t *stsc_data
     * }
     */
    public static MemorySegment stsc_data(MemorySegment struct) {
        return struct.get(stsc_data$LAYOUT, stsc_data$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * crx_sample_to_chunk_t *stsc_data
     * }
     */
    public static void stsc_data(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(stsc_data$LAYOUT, stsc_data$OFFSET, fieldValue);
    }

    private static final OfInt stsc_count$LAYOUT = (OfInt)$LAYOUT.select(groupElement("stsc_count"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * uint32_t stsc_count
     * }
     */
    public static final OfInt stsc_count$layout() {
        return stsc_count$LAYOUT;
    }

    private static final long stsc_count$OFFSET = 88;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * uint32_t stsc_count
     * }
     */
    public static final long stsc_count$offset() {
        return stsc_count$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * uint32_t stsc_count
     * }
     */
    public static int stsc_count(MemorySegment struct) {
        return struct.get(stsc_count$LAYOUT, stsc_count$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * uint32_t stsc_count
     * }
     */
    public static void stsc_count(MemorySegment struct, int fieldValue) {
        struct.set(stsc_count$LAYOUT, stsc_count$OFFSET, fieldValue);
    }

    private static final OfInt sample_count$LAYOUT = (OfInt)$LAYOUT.select(groupElement("sample_count"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * uint32_t sample_count
     * }
     */
    public static final OfInt sample_count$layout() {
        return sample_count$LAYOUT;
    }

    private static final long sample_count$OFFSET = 92;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * uint32_t sample_count
     * }
     */
    public static final long sample_count$offset() {
        return sample_count$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * uint32_t sample_count
     * }
     */
    public static int sample_count(MemorySegment struct) {
        return struct.get(sample_count$LAYOUT, sample_count$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * uint32_t sample_count
     * }
     */
    public static void sample_count(MemorySegment struct, int fieldValue) {
        struct.set(sample_count$LAYOUT, sample_count$OFFSET, fieldValue);
    }

    private static final OfInt sample_size$LAYOUT = (OfInt)$LAYOUT.select(groupElement("sample_size"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * uint32_t sample_size
     * }
     */
    public static final OfInt sample_size$layout() {
        return sample_size$LAYOUT;
    }

    private static final long sample_size$OFFSET = 96;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * uint32_t sample_size
     * }
     */
    public static final long sample_size$offset() {
        return sample_size$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * uint32_t sample_size
     * }
     */
    public static int sample_size(MemorySegment struct) {
        return struct.get(sample_size$LAYOUT, sample_size$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * uint32_t sample_size
     * }
     */
    public static void sample_size(MemorySegment struct, int fieldValue) {
        struct.set(sample_size$LAYOUT, sample_size$OFFSET, fieldValue);
    }

    private static final AddressLayout sample_sizes$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("sample_sizes"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * int32_t *sample_sizes
     * }
     */
    public static final AddressLayout sample_sizes$layout() {
        return sample_sizes$LAYOUT;
    }

    private static final long sample_sizes$OFFSET = 104;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * int32_t *sample_sizes
     * }
     */
    public static final long sample_sizes$offset() {
        return sample_sizes$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * int32_t *sample_sizes
     * }
     */
    public static MemorySegment sample_sizes(MemorySegment struct) {
        return struct.get(sample_sizes$LAYOUT, sample_sizes$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * int32_t *sample_sizes
     * }
     */
    public static void sample_sizes(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(sample_sizes$LAYOUT, sample_sizes$OFFSET, fieldValue);
    }

    private static final OfInt chunk_count$LAYOUT = (OfInt)$LAYOUT.select(groupElement("chunk_count"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * uint32_t chunk_count
     * }
     */
    public static final OfInt chunk_count$layout() {
        return chunk_count$LAYOUT;
    }

    private static final long chunk_count$OFFSET = 112;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * uint32_t chunk_count
     * }
     */
    public static final long chunk_count$offset() {
        return chunk_count$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * uint32_t chunk_count
     * }
     */
    public static int chunk_count(MemorySegment struct) {
        return struct.get(chunk_count$LAYOUT, chunk_count$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * uint32_t chunk_count
     * }
     */
    public static void chunk_count(MemorySegment struct, int fieldValue) {
        struct.set(chunk_count$LAYOUT, chunk_count$OFFSET, fieldValue);
    }

    private static final AddressLayout chunk_offsets$LAYOUT = (AddressLayout)$LAYOUT.select(groupElement("chunk_offsets"));

    /**
     * Layout for field:
     * {@snippet lang=c :
     * INT64 *chunk_offsets
     * }
     */
    public static final AddressLayout chunk_offsets$layout() {
        return chunk_offsets$LAYOUT;
    }

    private static final long chunk_offsets$OFFSET = 120;

    /**
     * Offset for field:
     * {@snippet lang=c :
     * INT64 *chunk_offsets
     * }
     */
    public static final long chunk_offsets$offset() {
        return chunk_offsets$OFFSET;
    }

    /**
     * Getter for field:
     * {@snippet lang=c :
     * INT64 *chunk_offsets
     * }
     */
    public static MemorySegment chunk_offsets(MemorySegment struct) {
        return struct.get(chunk_offsets$LAYOUT, chunk_offsets$OFFSET);
    }

    /**
     * Setter for field:
     * {@snippet lang=c :
     * INT64 *chunk_offsets
     * }
     */
    public static void chunk_offsets(MemorySegment struct, MemorySegment fieldValue) {
        struct.set(chunk_offsets$LAYOUT, chunk_offsets$OFFSET, fieldValue);
    }

    /**
     * Obtains a slice of {@code arrayParam} which selects the array element at {@code index}.
     * The returned segment has address {@code arrayParam.address() + index * layout().byteSize()}
     */
    public static MemorySegment asSlice(MemorySegment array, long index) {
        return array.asSlice(layout().byteSize() * index);
    }

    /**
     * The size (in bytes) of this struct
     */
    public static long sizeof() { return layout().byteSize(); }

    /**
     * Allocate a segment of size {@code layout().byteSize()} using {@code allocator}
     */
    public static MemorySegment allocate(SegmentAllocator allocator) {
        return allocator.allocate(layout());
    }

    /**
     * Allocate an array of size {@code elementCount} using {@code allocator}.
     * The returned segment has size {@code elementCount * layout().byteSize()}.
     */
    public static MemorySegment allocateArray(long elementCount, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(elementCount, layout()));
    }

    /**
     * Reinterprets {@code addr} using target {@code arena} and {@code cleanupAction) (if any).
     * The returned segment has size {@code layout().byteSize()}
     */
    public static MemorySegment reinterpret(MemorySegment addr, Arena arena, Consumer<MemorySegment> cleanup) {
        return reinterpret(addr, 1, arena, cleanup);
    }

    /**
     * Reinterprets {@code addr} using target {@code arena} and {@code cleanupAction) (if any).
     * The returned segment has size {@code elementCount * layout().byteSize()}
     */
    public static MemorySegment reinterpret(MemorySegment addr, long elementCount, Arena arena, Consumer<MemorySegment> cleanup) {
        return addr.reinterpret(layout().byteSize() * elementCount, arena, cleanup);
    }
}
