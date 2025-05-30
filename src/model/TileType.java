package model;

/**
 * Enumerasi jenis tile yang mungkin ada di FarmMap.
 * Digunakan untuk menentukan fungsi dan tampilan tile,
 * serta digunakan dalam pengecekan collision (isWalkable).
 */
public enum TileType {
    EMPTY,         // Kosong, tidak bisa dilewati
    TILLABLE,      // Bisa dibajak (.), bisa dilewati
    TILLED,        // Sudah dibajak (t), bisa ditanami, bisa dilewati
    PLANTED,       // Sudah ditanami (l), bisa dilewati
    HOUSE,         // Rumah pemain (h), tidak bisa dilewati
    POND,          // Kolam (o), tidak bisa dilewati
    SHIPPING_BIN,  // Tempat jual (s), tidak bisa dilewati
    OBSTACLE       // Tambahan seperti pohon, batu (x), tidak bisa dilewati
}