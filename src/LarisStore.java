import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class LarisStore {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Barang> daftarBarang = new ArrayList<>();
        ArrayList<Akun> daftarAkun = new ArrayList<>();
        buatBarang(daftarBarang);
        int pilih, kesalahanLogin = 0;
        Akun current = null;
        boolean masuk = false;
        do {
            Aktivitas aktivitas;
            System.out.println("SELAMAT DATANG DI LARIS STORE");
            tampilkanMenu();
            System.out.print("SILAHKAN MASUKKAN PILIHAN ANDA : ");
            pilih = sc.nextInt();
            sc.nextLine();
            switch (pilih) {
                case 0:
                    System.out.println("TERIMA KASIH!");
                    break;
                case 1:
                    aktivitas = new Aktivitas();
                    Akun a = (aktivitas.register(sc));
                    daftarAkun.add(a);
                    System.out.println("Akun berhasil dibuat. Berikut informasi akun Anda : ");
                    tampilkanInfoAkun(a);
                    System.out.println("Silahkan login!");
                    break;
                case 2:
                    aktivitas = new Aktivitas();
                    String[] dataLogin = (aktivitas.login(sc, daftarAkun));
                    boolean ditemukan = false;
                    for (Akun a1 : daftarAkun) {
                        if (a1.getNama().equalsIgnoreCase(dataLogin[0])) {
                            if (a1.getId().equals(dataLogin[1])) {
                                if (a1.getPin().equals(dataLogin[2])) {
                                    ditemukan = true;
                                    current = a1;
                                    masuk = true;
                                    kesalahanLogin = 0;
                                    break;
                                } else {

                                    System.out.println("Pin Anda salah. Kesempatan login anda tinggal bersisa "
                                            + (2 - kesalahanLogin));
                                    kesalahanLogin++;
                                    if (kesalahanLogin >= 3) {
                                        a1.blokirAkun();
                                        System.out.println(
                                                "Akun Anda telah diblokir karena kesalahan login berturutturut.");
                                    }
                                }
                            }
                        }
                    }
                    if (!ditemukan) {
                        System.out.println("Akun tidak ditemukan. Silahkan register terlebih dahulu!");
                    }
                    break;
                case 3:
                    if (masuk == false) {
                        System.out.println("Silahkan login terlebih dahulu!");
                        break;
                    } else {
                        tampilkanInfoAkun(current);
                    }
                    break;
                case 4:
                    if (masuk == false) {
                        System.out.println("Silahkan login terlebih dahulu!");
                        break;
                    } else {
                        System.out.println("Anda sedang melakukan top up.");
                        System.out.println("Masukkan nominal : ");
                        double x = sc.nextDouble();
                        sc.nextLine();
                        current.tambahSaldo(x);
                        System.out.println("Saldo berhasil ditambah. Terima kasih!");
                    }
                    break;
                case 5:
                    if (masuk == false) {
                        System.out.println("Silahkan login terlebih dahulu!");
                        break;
                    } else {
                        tampilkanBarang(daftarBarang);
                        double jumlahHarga = 0;
                        Barang barangDitemukan;
                        System.out.println("Masukkan jumlah dari jenis barang yang ingin dibeli : ");
                        int banyak = sc.nextInt();
                        sc.nextLine();
                        System.out.println("Masukkan barang-barang yang ingin dibeli !");
                        int[] idBarang = new int[banyak];
                        int[] banyakBarang = new int[banyak];
                        boolean[] ada = new boolean[banyak];
                        for (int i = 0; i < banyak; i++) {
                            System.out.print("ID barang" + (i + 1) + " (1-17) : ");
                            idBarang[i] = sc.nextInt();
                            barangDitemukan = cariBarang(daftarBarang, idBarang[i]);
                            if (barangDitemukan != null) {

                                System.out.println("Barang ditemukan: " +
                                        barangDitemukan.getNamaBarang());
                                ada[i] = true;
                            } else {

                                System.out.println("Barang dengan ID " + idBarang[i] + " tidak ditemukan.");
                                ada[i] = false;
                            }
                            if (ada[i]) {

                                System.out.print("Kuantitas barang " + (i + 1) + " : ");
                                banyakBarang[i] = sc.nextInt();
                                jumlahHarga += banyakBarang[i] * barangDitemukan.getHargaBarang();
                                sc.nextLine();
                            }
                        }
                        // Hitung diskon dan cashback
                        double diskon = Aktivitas.hitungDiskon(jumlahHarga, current.getId());
                        double cashback = Aktivitas.hitungCashback(jumlahHarga, current.getId());
                        // Kurangi saldo sesuai total harga yang harus dibayar (setelah diskon,
                        // tambahkan cashback)
                        double totalHarga = jumlahHarga
                                - diskon + cashback;
                        if (totalHarga > current.getSaldo()) {
                            System.out.println("Maaf, saldo Anda tidak mencukupi.");
                        } else {

                            current.kurangiSaldo(totalHarga);

                            System.out.println("Pembelian berhasil!");
                            System.out
                                    .println("Total harga: " +
                                            DecimalFormat.getCurrencyInstance().format(jumlahHarga));
                            System.out.println("Diskon: " + DecimalFormat.getCurrencyInstance().format(diskon));

                            System.out.println("Cashback: " +
                                    DecimalFormat.getCurrencyInstance().format(cashback));
                            System.out.println("Saldo sekarang: "
                                    +
                                    DecimalFormat.getCurrencyInstance().format(current.getSaldo()));
                        }
                    }
                    break;
                case 6:
                    if (masuk == false) {
                        System.out.println("Silahkan login terlebih dahulu!");
                        break;
                    } else {
                        current = null;
                        masuk = false;
                    }
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silahkan pilih kembali!");
                    break;
            }
        } while (pilih > 0 && pilih < 7);
        sc.close();
    }

    public static void tampilkanMenu() {
        System.out.println("MENU : ");
        System.out.println("0. Exit");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Tampilkan Informasi Akun");
        System.out.println("4. Top Up");
        System.out.println("5. Pembelian");
        System.out.println("6. Log out");
    }

    public static void buatBarang(ArrayList<Barang> daftarBarang) {
        daftarBarang.add(new Barang("Botol", 50000, 1));
        daftarBarang.add(new Barang("Kemeja", 100000,
                2));
        daftarBarang.add(new Barang("Smartphone",
                1000000, 3));
        daftarBarang.add(new Barang("Laptop", 2000000,
                4));
        daftarBarang.add(new Barang("Mouse", 50000, 5));
        daftarBarang.add(new Barang("Keyboard", 100000,
                6));
        daftarBarang.add(new Barang("Headphones",
                150000, 7));
        daftarBarang.add(new Barang("Smartwatch",
                300000, 8));
        daftarBarang.add(new Barang("Printer", 400000,
                9));
        daftarBarang.add(new Barang("Camera", 800000,
                10));
        daftarBarang.add(new Barang("Tablet", 700000,
                11));
        daftarBarang.add(new Barang("Monitor", 900000,
                12));
        daftarBarang.add(new Barang("Speaker", 200000,
                13));
        daftarBarang.add(new Barang("Projector", 700000,
                14));
        daftarBarang.add(new Barang("Coffee Maker",
                500000, 15));
        daftarBarang.add(new Barang("Tas Ransel",
                200000, 16));
        daftarBarang.add(new Barang("Kacamata Hitam",
                80000, 17));
    }

    public static void tampilkanBarang(ArrayList<Barang> daftarBarang) {
        System.out.println("Daftar Barang di Marketplace:");
        for (Barang barang : daftarBarang) {
            System.out
                    .println(barang.getIdBarang() + ". "
                            + barang.getNamaBarang() + " - Rp" +
                            barang.getHargaBarang());
        }
    }

    public static void tampilkanInfoAkun(Akun akun) {
        System.out.println("Nama : " + akun.getNama());
        System.out.println("ID : " + akun.getId());
        System.out.println("Saldo : " +
                DecimalFormat.getCurrencyInstance().format(akun.getSaldo()));
    }

    public static Barang cariBarang(ArrayList<Barang> daftarBarang, int id) {
        for (Barang barang : daftarBarang) {
            if (barang.getIdBarang() == id) {
                return barang;
            }
        }
        return null;
    }
}