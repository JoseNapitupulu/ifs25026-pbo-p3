package adapter.presenter;
import domain.entity.Activity;
import java.util.List;
public class ActivityPresenter extends ConsolePresenter {
    public String format(Activity a) { return String.format("[%d] %s | Hari: %s | Pukul: %s", a.getId(), a.getTitle(), a.getDay(), a.getTime()); }
    public void dashboard(List<Activity> list) { printLine("Daftar Kegiatan:"); if (list.isEmpty()) printLine("- Data kegiatan belum tersedia!"); else show(list); }
    public void show(List<Activity> list) { printList(list, this::format); }
    public void added(Activity a) { printLine("Berhasil menambah kegiatan: " + format(a)); printBlankLine(); }
    public void updated(Activity a) { printLine("Berhasil mengubah kegiatan."); printBlankLine(); }
    public void search(String q, List<Activity> list) { printLine("Hasil Pencarian: \"" + q + "\""); if (list.isEmpty()) printLine("- Kegiatan tidak ditemukan!"); else show(list); printBlankLine(); }
    public void sorted(List<Activity> list) { printLine("Daftar Kegiatan (Terurut):"); show(list); printBlankLine(); }
    public void deleted() { printLine("Berhasil menghapus kegiatan."); printBlankLine(); }
    public void message(String text) { printLine(text); }
}