import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Aktivitas {
    private Akun akun;
    private int count = 0;

    public Aktivitas() {
    }

    public Aktivitas(Akun akun) {
        this.akun = akun;
    }

    public Akun register(Scanner sc) {
        System.out.println("Membership Laris Store :");
        System.out.println("1. Silver");
        System.out.println(
                "Keuntungan yang didapatkan : setiap pembelian di atas 3 juta akan mendapat diskon sebesar 3%");
        System.out.println("2. Gold");
        System.out.println(
                "Keuntungan yang didapatkan : setiap pembelian di atas 3 juta akan mendapat diskon sebesar 5%, selain itu cashback 2%");
        System.out.println("3. Platinum");
        System.out.println(
                "Keuntungan yang didapatkan : setiap pembelian di atas 3 juta akan mendapat diskon sebesar 7%, selain itu cashback 3%\n");
        System.out.println("Silahkan masukkan data untuk membuat akun baru.");
        System.out.print("Nama : ");
        String nama = sc.nextLine();
        String member, pin;
        boolean ok = false;
        String id = "";
        do {
            System.out.print("Member (silver, gold, platinum) : ");
            member = sc.nextLine();
            if (member.equals("silver")) {
                ok = true;
                count++;
                id = "426" + tambahkanNolDepan(count,
                        7);
            } else if (member.equals("gold")) {
                ok = true;
                count++;
                id = "591" + tambahkanNolDepan(count,
                        7);
            } else if (member.equals("platinum")) {
                ok = true;
                count++;
                id = "772" + tambahkanNolDepan(count,
                        7);
            } else {
                System.out.println("Pilihan member tidak tersedia. Pilih lagi");
            }
        } while (!ok); // Perbaikan disini
        System.out.println("Buat pin anda 6 digit : ");
        pin = sc.nextLine();
        Akun akun = new Akun(nama, id, 0, pin);
        this.akun = akun;
        return akun;
    }

    public String[] login(Scanner sc, ArrayList<Akun> daftarAkun) {
        System.out.println("Masukkan data untuk login!");
        String[] data = new String[3];
        System.out.print("Nama : ");
        data[0] = sc.nextLine();
        System.out.print("ID : ");
        data[1] = sc.nextLine();
        System.out.print("Pin : ");
        data[2] = sc.nextLine();
        return data;
    }

    public static String tambahkanNolDepan(int angka,
            int panjang) {
        String angkaStr = String.valueOf(angka);
        int nolYangDitambahkan = Math.max(0, panjang -
                angkaStr.length());
        StringBuilder hasil = new StringBuilder();
        for (int i = 0; i < nolYangDitambahkan; i++) {
            hasil.append("0");
        }
        hasil.append(angkaStr);
        return hasil.toString();
    }

    public static double hitungDiskon(double totalBelanja, String idMember) {
        double diskon = 0;
        if (idMember.startsWith("426") && totalBelanja > 3000000) {
            diskon = 0.03 * totalBelanja;
        } else if (idMember.startsWith("591") &&
                totalBelanja > 3000000) {
            diskon = 0.05 * totalBelanja;
        } else if (idMember.startsWith("772") &&
                totalBelanja > 3000000) {
            diskon = 0.07 * totalBelanja;
        }
        return diskon;
    }

    public static double hitungCashback(double totalBelanja, String idMember) {
        double cashback = 0;
        if (idMember.startsWith("591") && totalBelanja > 3000000) {
            cashback = 0.02 * totalBelanja;
        } else if (idMember.startsWith("772") &&
                totalBelanja > 3000000) {
            cashback = 0.03 * totalBelanja;
        }
        return cashback;
    }
}
