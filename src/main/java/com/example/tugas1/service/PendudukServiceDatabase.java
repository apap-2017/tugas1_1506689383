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
		return pendudukMapper.selectPenduduk(nik);
	}

	@Override
	public boolean tambahPenduduk(PendudukModel penduduk) {
		
		if (penduduk.getNama() != null && penduduk.getNama() != null && penduduk.getTempat_lahir()
				!= null && penduduk.getTanggal_lahir() != null && penduduk.getJenis_kelamin() != null && penduduk.getGolongan_darah()
				!= null && penduduk.getAgama() != null && penduduk.getStatus_perkawinan() != null && penduduk.getPekerjaan()
				!= null && penduduk.getIs_wni() != null && penduduk.getIs_wafat() != null && penduduk.getId_keluarga()
				!= null && penduduk.getStatus_dalam_keluarga() != null) {
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
		if (penduduk.getNama() != null && penduduk.getNama() != null && penduduk.getTempat_lahir()
				!= null && penduduk.getTanggal_lahir() != null && penduduk.getJenis_kelamin() != null && penduduk.getGolongan_darah()
				!= null && penduduk.getAgama() != null && penduduk.getStatus_perkawinan() != null && penduduk.getPekerjaan()
				!= null && penduduk.getIs_wni() != null && penduduk.getIs_wafat() != null && penduduk.getId_keluarga()
				!= null && penduduk.getStatus_dalam_keluarga() != null) {
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
					penduduk.getStatus_perkawinan(), penduduk.getPekerjaan(), penduduk.getIs_wni(), penduduk.getIs_wafat(),
					penduduk.getId_keluarga(), penduduk.getJenis_kelamin(), penduduk.getStatus_dalam_keluarga(), nikLama);
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
		List<PendudukModel> total = pendudukMapper.selectPenduduksByIdFam(fam.get(0));
		;

		for (int i = 1; i < fam.size(); i++) {
			List<PendudukModel> tmp = pendudukMapper.selectPenduduksByIdFam(fam.get(i));
			System.out.println(i + " " + fam.get(i) + " " + tmp.size());
			total.addAll(tmp);
		}

		log.info(total.size() + "");

		return total;
	}

}
