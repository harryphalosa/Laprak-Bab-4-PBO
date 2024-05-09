class Barang {
    private String namaBarang;
    private double hargaBarang;
    private int idBarang;

    public Barang(String nama, double harga, int id) {
        this.namaBarang = nama;
        this.hargaBarang = harga;
        this.idBarang = id;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public double getHargaBarang() {
        return hargaBarang;
    }

    public int getIdBarang() {
        return idBarang;
    }
}
