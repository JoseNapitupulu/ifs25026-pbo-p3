package adapter.presenter;
import domain.entity.Activity;
import java.util.List;
public class ActivityPresenter {
    public String format(Activity a) { return String.format("[%d] %s | Hari: %s | Pukul: %s", a.getId(), a.getTitle(), a.getDay(), a.getTime()); }
    public void dashboard(List<Activity> list) { System.out.println("Daftar Kegiatan:"); if (list.isEmpty()) System.out.println("- Data kegiatan belum tersedia!"); else show(list); }
    public void show(List<Activity> list) { for (Activity a : list) System.out.println(format(a)); }
    public void added(Activity a) { System.out.println("Berhasil menambah kegiatan: " + format(a)); System.out.println(); }
    public void updated(Activity a) { System.out.println("Berhasil mengubah kegiatan."); System.out.println(); }
    public void search(String q, List<Activity> list) { System.out.println("Hasil Pencarian: \"" + q + "\""); if (list.isEmpty()) System.out.println("- Kegiatan tidak ditemukan!"); else show(list); System.out.println(); }
    public void sorted(List<Activity> list) { System.out.println("Daftar Kegiatan (Terurut):"); show(list); System.out.println(); }
    public void deleted() { System.out.println("Berhasil menghapus kegiatan."); System.out.println(); }
    public void message(String text) { System.out.println(text); }
}