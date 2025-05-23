package model;

public interface Action {
    /**
     * Menjalankan aksi dengan parameter pemain, farm, dan argumen tambahan.
     *
     * @param player pemain yang menjalankan aksi
     * @param farm   objek Farm tempat aksi dilakukan
     * @param args   argumen tambahan, bisa berupa koordinat atau nama item
     */
    void execute(Player player, Farm farm, String args);

    /**
     * Mengecek apakah aksi bisa dieksekusi oleh pemain saat ini.
     *
     * @param player pemain yang akan menjalankan aksi
     * @return true jika bisa dieksekusi, false jika tidak
     */
    boolean isExecutable(Player player);
}
