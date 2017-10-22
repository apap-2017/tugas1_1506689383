package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tugas1.model.KecamatanModel;
import com.example.tugas1.model.KeluargaModel;
import com.example.tugas1.model.KelurahanModel;
import com.example.tugas1.model.KotaModel;
import com.example.tugas1.model.PendudukModel;

@Mapper
public interface KeluargaMapper {

	@Select("select nomor_kk, alamat, rt, rw, nama_kelurahan, nama_kecamatan, nama_kota, k.id as id_kel"
			+ " from keluarga k join kelurahan kl" + " on id_kelurahan = kl.id join kecamatan kc"
			+ " on id_kecamatan = kc.id join kota ko" + " on id_kota = ko.id where nomor_kk=#{nkk}")
	@Results(value = { @Result(property = "nomor_kk", column = "nomor_kk"),
			@Result(property = "alamat", column = "alamat"), @Result(property = "rt", column = "rt"),
			@Result(property = "rw", column = "rw"), @Result(property = "nama_kelurahan", column = "nama_kelurahan"),
			@Result(property = "nama_kecamatan", column = "nama_kecamatan"),
			@Result(property = "nama_kota", column = "nama_kota"),
			@Result(property = "penduduks", column = "id_kel", javaType = List.class, many = @Many(select = "selectPenduduksByIdFam")) })
	KeluargaModel selectKeluarga(@Param("nkk") String nkk);
	
	@Select("select nama, nik, jenis_kelamin, tempat_lahir, tanggal_lahir, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, is_wni, is_wafat from penduduk where id_keluarga=#{id_keluarga}")
	List<PendudukModel> selectPenduduksByIdFam(@Param("id_keluarga") String id_keluarga);

	@Select("select kode_kecamatan" + " from keluarga k join kelurahan kl"
			+ " on id_kelurahan = kl.id join kecamatan kc" + "d where k.id=#{id}")
	KeluargaModel selectById(@Param("id") String id);

	@Insert("insert into keluarga (nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku) values (#{nomor_kk}, #{alamat}, #{rt}, #{rw}, #{id_kelurahan}, #{is_tidak_berlaku})")
	void tambahKeluarga(KeluargaModel keluarga);

	@Select("select id, id_kecamatan from kelurahan where nama_kelurahan=lcase(#{nama_kelurahan})")
	KelurahanModel getIdKel(@Param("nama_kelurahan") String nama_kelurahan);

	@Select("select id, id_kota, kode_kecamatan from kecamatan where nama_kecamatan=lcase(#{nama_kecamatan})")
	KecamatanModel getIdKec(@Param("nama_kecamatan") String nama_kecamatan);

	@Select("select id from kota where nama_kota=lcase(#{nama_kota})")
	KotaModel getIdKota(@Param("nama_kota") String nama_kota);

	@Select("select count(id) from keluarga where nomor_kk like #{nomor_kk} ")
	int selectNkk(@Param("nomor_kk") String nomor_kk);
	
	@Select("select nomor_kk from keluarga where nomor_kk like #{nomor_kk} ")
	List<KeluargaModel> selectNKK(@Param("nomor_kk") String nomor_kk);

	@Update("update keluarga set nomor_kk=#{nomor_kk}, alamat=#{alamat}, rt=#{rt}, rw=#{rw}, id_kelurahan=#{id_kelurahan} where nomor_kk=#{nkkLama}")
	void ubahKeluarga(@Param("nomor_kk") String nomor_kk,@Param("alamat") String alamat, @Param("rt") String rt, @Param("rw") String rw,
			@Param("id_kelurahan") String id_kelurahan, @Param("nkkLama") String nkkLama);
	
	@Select("Select id from keluarga where id_kelurahan=#{id_kelurahan}")
	List<String> getKeluarga(@Param("id_kelurahan") String id_kelurahan);
}
