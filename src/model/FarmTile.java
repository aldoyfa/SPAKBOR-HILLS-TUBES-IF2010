package model;

public class FarmTile {
    private boolean tilled = false;
    private Seed plantedSeed = null;
    private boolean watered = false;

    // Mengecek apakah tile sudah dicangkul
    public boolean isTilled() {
        return tilled;
    }

    // Alias dari isTilled agar lebih mudah dibaca saat recovery
    public boolean isSoil() {
        return isTilled();
    }

    // Mencangkul tanah
    public void till() {
        tilled = true;
        watered = false; // saat dicangkul ulang, belum disiram
    }

    // Cek apakah bisa dicangkul (belum dicangkul dan tidak ada tanaman)
    public boolean canBeTilled() {
        return !tilled && plantedSeed == null;
    }

    // Cek apakah belum ada tanaman
    public boolean isEmpty() {
        return plantedSeed == null;
    }

    // Menanam benih
    public void plant(Seed seed) {
        if (tilled && isEmpty()) {
            this.plantedSeed = seed;
            this.watered = false;
        }
    }

    // Ambil tanaman
    public Seed getPlantedSeed() {
        return plantedSeed;
    }

    // Hapus tanaman
    public void removePlant() {
        this.plantedSeed = null;
    }

    // Cek apakah ada tanaman
    public boolean hasPlant() {
        return plantedSeed != null;
    }

    // Menyiram tanaman
    public void water() {
        if (tilled && hasPlant()) {
            watered = true;
        }
    }

    // Apakah sudah disiram hari ini
    public boolean isWatered() {
        return watered;
    }

    // Reset tile kembali jadi tanah biasa
    public void resetToLand() {
        tilled = false;
        plantedSeed = null;
        watered = false;
    }
}
