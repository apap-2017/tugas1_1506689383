package com.example.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.tugas1.dao.DaerahMapper;
import com.example.tugas1.dao.KeluargaMapper;
import com.example.tugas1.dao.PendudukMapper;
import com.example.tugas1.model.PendudukModel;
import com.example.tugas1.model.KecamatanModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PendudukServiceDatabase implements PendudukService {
	@Autowired
	private PendudukMapper pendudukMapper;
	@Autowired
	private DaerahMapper daerahMapper;
	@Autowired
	private KeluargaMapper keluargaMapper;

	@Override
	public PendudukModel selectPenduduk(String nik) {
		PendudukModel a = pendudukMapper.selectPenduduk(nik);
		log.info(a.getTanggal_lahir());
		String[] split = a.getTanggal_lahir().split("-");
		String tanggal = "";
		if (split[1].equals("01")) {
			tanggal = split[2] + " Januari " + split[0];
		} else if (split[1].equals("02")) {
			tanggal = split[2] + " Februari " + split[0];
		} else if (split[1].equals("03")) {
			tanggal = split[2] + " Maret " + split[0];
		} else if (split[1].equals("04")) {
			tanggal = split[2] + " April " + split[0];
		} else if (split[1].equals("05")) {
			tanggal = split[2] + " Mei " + split[0];
		} else if (split[1].equals("06")) {
			tanggal = split[2] + " Juni " + split[0];
		} else if (split[1].equals("07")) {
			tanggal = split[2] + " Juli " + split[0];
		} else if (split[1].equals("08")) {
			tanggal = split[2] + " Agustus " + split[0];
		} else if (split[1].equals("09")) {
			tanggal = split[2] + " September " + split[0];
		} else if (split[1].equals("10")) {
			tanggal = split[2] + " Oktober " + split[0];
		} else if (split[1].equals("11")) {
			tanggal = split[2] + " November " + split[0];
		} else if (split[1].equals("12")) {
			tanggal = split[2] + " Desember " + split[0];
		}
		
		a.setTanggal_lahir(tanggal);
		return a;
	}

	@Override
	public boolean tambahPenduduk(PendudukModel penduduk) {

		if (penduduk.getNama() != null && penduduk.getNama() != null && penduduk.getTempat_lahir() != null
				&& penduduk.getTanggal_lahir() != null && penduduk.getJenis_kelamin() != null
				&& penduduk.getGolongan_darah() != null && penduduk.getAgama() != null
				&& penduduk.getStatus_perkawinan() != null && penduduk.getPekerjaan() != null
				&& penduduk.getIs_wni() != null && penduduk.getIs_wafat() != null && penduduk.getId_keluarga() != null
				&& penduduk.getStatus_dalam_keluarga() != null) {
			String nik = this.validasiNik(penduduk);
			penduduk.setNik(nik);
			pendudukMapper.tambahPenduduk(penduduk);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean ubahPenduduk(PendudukModel penduduk) {
		if (penduduk.getNama() != null && penduduk.getNama() != null && penduduk.getTempat_lahir() != null
				&& penduduk.getTanggal_lahir() != null && penduduk.getJenis_kelamin() != null
				&& penduduk.getGolongan_darah() != null && penduduk.getAgama() != null
				&& penduduk.getStatus_perkawinan() != null && penduduk.getPekerjaan() != null
				&& penduduk.getIs_wni() != null && penduduk.getIs_wafat() != null && penduduk.getId_keluarga() != null
				&& penduduk.getStatus_dalam_keluarga() != null) {
			String nikLama = penduduk.getNik();
			PendudukModel pendudukLama = pendudukMapper.selectPenduduk(nikLama);

			if (!(pendudukLama.getId_keluarga().equals(penduduk.getId_keluarga()))
					|| !(penduduk.getTanggal_lahir().equals(pendudukLama.getTanggal_lahir()))
					|| !(penduduk.getJenis_kelamin().equals(pendudukLama.getJenis_kelamin()))) {
				String nik = this.validasiNik(penduduk);
				penduduk.setNik(nik);
			}

			pendudukMapper.ubahPenduduk(penduduk.getNik(), penduduk.getNama(), penduduk.getTempat_lahir(),
					penduduk.getTanggal_lahir(), penduduk.getGolongan_darah(), penduduk.getAgama(),
					penduduk.getStatus_perkawinan(), penduduk.getPekerjaan(), penduduk.getIs_wni(),
					penduduk.getIs_wafat(), penduduk.getId_keluarga(), penduduk.getJenis_kelamin(),
					penduduk.getStatus_dalam_keluarga(), nikLama);
			return true;
		} else {
			return false;
		}

	}

	public String validasiNik(PendudukModel penduduk) {
		String idkeluarga = penduduk.getId_keluarga();
		KecamatanModel kec = daerahMapper.selectKode(idkeluarga);
		String nik = kec.getKode_kecamatan().substring(0, 6);
		String[] tgl = penduduk.getTanggal_lahir().split("-");
		tgl[0] = tgl[0].substring(2, 4);
		String tglfix = tgl[2] + tgl[1] + tgl[0];
		int kode = 1;

		if (penduduk.getJenis_kelamin().equals("1")) {
			int tgltmp = Integer.parseInt(tgl[2]) + 40;
			tglfix = tgltmp + tgl[1] + tgl[0];
		}

		String cek = nik + tglfix + "%";
		int add = 0;
		List<PendudukModel> a = pendudukMapper.selectNIK(cek);
		String tes = "" + a.size();

		log.info(tes);

		for (int i = 0; i < a.size(); i++) {
			String last = a.get(i).getNik().substring(12, 16);
			add = Integer.parseInt(last);
		}

		kode = kode + add;
		String akhir = "";
		if (kode < 10) {
			akhir = "000" + kode;
		} else if (kode >= 10 && kode <= 99) {
			akhir = "00" + kode;
		} else if (kode > 99 && kode <= 999) {
			akhir = "0" + kode;
		} else {
			akhir = "" + kode;
		}

		nik = nik + tglfix + akhir;

		return nik;
	}

	@Override
	public void nonaktifPenduduk(PendudukModel penduduk) {
		penduduk.setIs_wafat("1");
		pendudukMapper.nonaktif(penduduk);

		List<PendudukModel> keluarga = keluargaMapper.selectPenduduksByIdFam(penduduk.getId_keluarga());
		int total = keluarga.size();
		int cek = 0;

		for (int i = 0; i < keluarga.size(); i++) {
			if (keluarga.get(i).getIs_wafat().equals("1")) {
				cek++;
			}
		}

		if (cek == total) {
			log.info(penduduk.getId_keluarga());
			pendudukMapper.setTidakBerlaku(penduduk.getId_keluarga());
		}
	}

	@Override
	public List<PendudukModel> pendudukKelurahan(String id_kelurahan) {
		List<String> fam = keluargaMapper.getKeluarga(id_kelurahan);
		List<PendudukModel> total = keluargaMapper.selectPenduduksByIdFam(fam.get(0));

		for (int i = 1; i < fam.size(); i++) {
			List<PendudukModel> tmp = keluargaMapper.selectPenduduksByIdFam(fam.get(i));
			System.out.println(i + " " + fam.get(i) + " " + tmp.size());
			total.addAll(tmp);
		}

		log.info(total.size() + "");

		return total;
	}

}
