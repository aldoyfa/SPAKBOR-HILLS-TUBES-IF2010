![Main Menu](https://github.com/aldoyfa/SPAKBOR-HILLS-TUBES-IF2010/blob/main/src/res/ui/Main%20Menu.png)

# TUGAS BESAR IF2010 Pemrograman Berorientasi Objek
## SPAKBOR HILLS: FARM AND BUILD RELATIONSHIPS!
Kelompok 6 Kelas K6 (STI Jatinangor) Semester Genap 2024/2025

Anggota Kelompok:

| NIM       | Nama                     |
|-----------|--------------------------|
| 18223113  | Aldoy Fauzan Avanza      |
| 18223121  | Fudhail Fayyadh          |
| 18223123  | Harfhan Ikhtiar Ahmad R  |
| 18223131  | Aulia Azka Azzahra       |
| 18223138  | Sonya Putri Fadilah      |

## About the Game
Spakbor Hills adalah permainan simulasi bertani berbasis teks yang dibuat untuk membantu Dr. Asep Spakbor menjadi petani sukses demi mencegah kekacauan dunia. Pemain dapat bertani, memancing, memasak, menjual hasil panen, serta menjalin hubungan dengan NPC. Setiap aksi membutuhkan strategi, energi, dan waktu dalam game. Tujuan akhir permainan adalah mengumpulkan 17.209 gold dan menikah dengan salah satu NPC.

## Cara Menjalankan Program
- *Untuk OS Windows:*
  ```text
  Klik tombol "Run Java" di VS Code
  
- *Game Play:*
Untuk memainkan permainan Spakbor Hills, gunakan tombol W, A, S, dan D untuk menggerakkan karakter. Menu Help dapat digunakan untuk melihat panduan lengkap mengenai aksi-aksi dalam permainan. Saat memilih New Game, pemain akan diminta untuk memasukkan nama karakter, nama kebun, dan jenis kelamin. Sepanjang permainan, pemain dapat melakukan berbagai aksi sesuai dengan tombol yang tercantum di menu Help. Jika ingin mengakhiri permainan, gunakan opsi Exit dari menu utama.

## Struktur Program

```
src/
├── action/
│   ├── Action.java
│   ├── EatingAction.java
│   ├── FishingAction.java
│   ├── HarvestingAction.java
│   ├── PlantingAction.java
│   ├── RecoverlandAction.java
│   ├── TillingAction.java
│   └── WateringAction.java
│
├── controller/
│   └── [isi file controller]
│
├── entity/
│   └── Player.java
│
├── inputs/
│   ├── KeyboardListener.java
│   └── MouseListener.java
│
├── main/
│   ├── GamePanel.java
│   └── Main.java
│
├── model/
│   ├── Crops.java
│   ├── EdibleItem.java
│   ├── Farm.java
│   ├── FarmTile.java
│   ├── Fish.java
│   ├── FishType.java
│   ├── Food.java
│   ├── Inventory.java
│   ├── Item.java
│   ├── Location.java
│   ├── Seed.java
│   ├── TileType.java
│   └── Time.java
│
├── object/
│   ├── House.java
│   ├── Object.java
│   ├── ObjectManager.java
│   ├── Pond.java
│   └── ShippingBin.java
│
├── res/
│   └── [berisi gambar dan asset pendukung]
│
├── save/
│   └── map01.txt
│
└── tile/
    ├── Tile.java
    └── TileManager.java
