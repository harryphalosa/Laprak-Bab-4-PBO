public class Akun {
    private String nama, id, pin;
    private double saldo;
    private boolean aktif;
    private int kesalahanLogin;

    public Akun() {
        this.nama = null;
        this.id = null;
        this.saldo = 0;
        this.pin = null;
        this.aktif = true;
        this.kesalahanLogin = 0;
    }

    public Akun(String nama, String id, double saldo) {
        this.nama = nama;
        this.id = id;
        this.saldo = saldo;
        this.aktif = true;
        this.kesalahanLogin = 0;
    }

    public Akun(String nama, String id, double saldo,
            String pin) {
        this.nama = nama;
        this.id = id;
        this.saldo = saldo;
        this.pin = pin;
        this.aktif = true;
        this.kesalahanLogin = 0;
    }

    public void tambahSaldo(double d) {
        this.saldo += d;
    }

    public void kurangiSaldo(double d) {
        this.saldo -= d;
    }

    public void kurangiKesalahanLogin() {
        this.kesalahanLogin--;
    }

    public void tambahKesalahanLogin() {
        this.kesalahanLogin++;
        if (this.kesalahanLogin >= 3) {
            this.blokirAkun();
        }
    }

    public void blokirAkun() {
        this.aktif = false;
    }

    public boolean isAktif() {
        return aktif;
    }

    public String getNama() {
        return nama;
    }

    public String getId() {
        return id;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getPin() {
        return pin;
    }
}