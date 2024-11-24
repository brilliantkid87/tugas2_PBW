import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static ArrayList<Menu> daftarMenu= new ArrayList<Menu>();
    private static ArrayList<OrderItem> pesanan = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeMenu();
        while (true) {
            System.out.println("\n=== SISTEM RESTORAN ===");
            System.out.println("1. Menu Pelanggan");
            System.out.println("2. Menu Pengelolaan");
            System.out.println("3. Keluar");
            System.out.println("Pilih menu: ");

            String pilihan = scanner.nextLine();
            switch (pilihan) {
                case "1":
                    menuPelanggan();
                    break;
                    case "2":
                        menuPengelolaan();
                        break;
                        case "3":
                            System.out.println("Terima kasih");
                            return;
                            default:
                                System.out.println("Pilih menu tidak valid");
            }
        }
    }
    private static void initializeMenu() {
        daftarMenu.add(new Menu("Nasi Goreng", 25000, "Makanan"));
        daftarMenu.add(new Menu("Mie Goreng", 23000, "Makanan"));
        daftarMenu.add(new Menu("Ayam Bakar", 30000, "Makanan"));
        daftarMenu.add(new Menu("Sate Ayam", 20000, "Makanan"));

        daftarMenu.add(new Menu("Es Teh", 5000, "Minuman"));
        daftarMenu.add(new Menu("Es Jeruk", 7000, "Minuman"));
        daftarMenu.add(new Menu("Kopi", 5000, "Minuman"));
        daftarMenu.add(new Menu("Es Campur", 12000, "Minuman"));
    }

    private static void tampikanMenu() {
        System.out.println("\n=== Menu Makanan ===");
        for (int i = 0; i < daftarMenu.size(); i++) {
            Menu menu = daftarMenu.get(i);
            if (menu.getKategori().equals("Makanan")) {
                System.out.printf("%d. %s - Rp %.0f\n", (i + 1), menu.getNama(), menu.getHarga());
            }
        }

        System.out.println("\n=== Menu Minuman ===");
        for (int i = 0; i < daftarMenu.size(); i++) {
            Menu menu = daftarMenu.get(i);
            if (menu.getKategori().equals("Minuman")) {
                System.out.printf("%d. %s - Rp %.0f\n", (i +1), menu.getNama(), menu.getHarga());
            }
        }
    }

    private static void menuPelanggan() {
        pesanan.clear();
        while (true) {
            tampikanMenu();
            System.out.println("\nPilih nomor menu (ketik 'selesai' untuk mengakhiri pesanan): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("selesai")) {
                if (!pesanan.isEmpty()) {
                    cetakStruk();
                }
                break;
            }

            try {
                int nomorMenu = Integer.parseInt(input);
                if (nomorMenu >= 1 && nomorMenu <= daftarMenu.size()) {
                    System.out.println("Jumlah Pesanan: ");
                    int jumlah = Integer.parseInt(scanner.nextLine());
                    if (jumlah > 0) {
                        pesanan.add(new OrderItem(daftarMenu.get(nomorMenu - 1), jumlah));
                    }

                } else {
                    System.out.println("Menu tidak tersedia");
                }
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid");
            }
        }
    }

    private static void cetakStruk() {
        double totalHarga = 0;
        System.out.println("\n=== STRUK PESANAN ===");
        System.out.println("Item - Jumlah - Harga = Total");
        System.out.println("-----------------------------");

        for (OrderItem item : pesanan) {
            double subTotal = item.getMenu().getHarga() * item.getJumlah();
            totalHarga += subTotal;
            System.out.printf("%s x%d - Rp %.0f = Rp %.0f\n", item.getMenu().getNama(), item.getJumlah(), item.getMenu().getHarga(), subTotal);

        }
        System.out.println("-----------------------------");
        System.out.printf("Subtotal: Rp %.0f\n", totalHarga);

        double pajak = totalHarga * 0.1;
        double biayaLayanan = 20000;
        System.out.printf("Pajak : Rp %.0f\n", pajak);
        System.out.printf("Biaya Layanan: Rp %.0f\n", biayaLayanan);

        double diskon = 0;
        if (totalHarga > 100000) {
            diskon = totalHarga * 0.1;
            System.out.printf("Diskon: Rp %.0f\n", diskon);
        }

        boolean promoMinuman = totalHarga > 50000;
        if (promoMinuman) {
            System.out.printf("Promo: beli 1 gratis 1 untuk Minuman");
        }
    }

    private static void menuPengelolaan() {
        while (true) {
            System.out.println("\n=== Menu Pengelolaan ===");
            System.out.println("1. Tambah Menu");
            System.out.println("2. Ubah Harga");
            System.out.println("3. Hapus Menu");
            System.out.println("4. Kembali");
            System.out.print("Pilih Menu");

            String pilihan = scanner.nextLine();
            switch (pilihan) {
                case "1":
                    tambahMenu();
                    break;
                    case "2":
                        ubahHarga();
                        break;
                        case "3":
                            hapusMenu();
                            break;
                            case "4":
                                return;
                                default:
                                    System.out.println("Pilih menu tidak valid");
            }
        }
    }

    private static void tambahMenu() {
        System.out.println("Nama Menu: ");
        String nama = scanner.nextLine();

        System.out.println("Harga: ");
        double harga = Double.parseDouble(scanner.nextLine());

        System.out.println("Kategori (MAkanan/Minuman): ");
        String kategori = scanner.nextLine();

        if (kategori.equalsIgnoreCase("MAKANAN")) {
            daftarMenu.add(new Menu(nama, harga, kategori));
            System.out.println("Pilih Menu berhasil ditambahkan");
        } else {
            System.out.println("Kategori tidak valid");
        }
    }

    private static void ubahHarga() {
        tampikanMenu();
        System.out.print("\nPilih nomor menu yang akan diubah: ");
        try {
            int nomor = Integer.parseInt(scanner.nextLine());
            if (nomor >= 1 && nomor <= daftarMenu.size()) {
                System.out.print("Masukkan harga baru: ");
                double hargaBaru = Double.parseDouble(scanner.nextLine());

                System.out.print("Yakin mengubah harga? (Ya/Tidak): ");
                String konformasi = scanner.nextLine();

                if (konformasi.equalsIgnoreCase("Ya")) {
                    daftarMenu.get(nomor - 1).setHarga(hargaBaru);
                    System.out.println("Harga berhasil diubah");
                }
            } else {
                System.out.println("Nomor menu tidak valid");
            }
        } catch (NumberFormatException e) {
            System.out.println("Input tidak valid");
        }
    }

    private static void hapusMenu() {
        tampikanMenu();
        System.out.print("\nPilih nomor menu yang akan dihapus: ");
        try {
            int nomor = Integer.parseInt(scanner.nextLine());
            if (nomor >= 1 && nomor <= daftarMenu.size()) {
                System.out.print("Yakin menghapus menu? (Ya/Tidak): ");
                String konfirmasi = scanner.nextLine();

                if (konfirmasi.equalsIgnoreCase("Ya")) {
                    daftarMenu.remove(nomor - 1);
                    System.out.println("Menu berhasil dihapus");
                }
            } else {
                System.out.println("Nomor menu tidak valid");
            }
        } catch (NumberFormatException e) {
            System.out.println("Input tidak valid");
        }
    }
}