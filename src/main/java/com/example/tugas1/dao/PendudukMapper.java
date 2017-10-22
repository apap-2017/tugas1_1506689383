package com.example.tugas1.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.example.tugas1.model.PendudukModel;

@Mapper
public interface PendudukMapper {

	@Select("select nik, nama, tempat_lahir, tanggal_lahir, alamat, rt, rw, nama_kelurahan, nama_kecamatan, nama_kota, golongan_darah, agama, status_perkawinan, pekerjaan, is_wni, is_wafat, id_keluarga, jenis_kelamin, status_dalam_keluarga"
			+ " from penduduk join keluarga k" + " on id_keluarga=k.id join kelurahan kl"
			+ " on id_kelurahan = kl.id join kecamatan kc" + " on id_kecamatan = kc.id join kota ko"
			+ " on id_kota = ko.id where nik=#{nik}")
	PendudukModel selectPenduduk(@Param("nik") String nik);

	@Insert("INSERT INTO penduduk (nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat) VALUES (#{nik}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin}, #{is_wni}, #{id_keluarga}, #{agama}, #{pekerjaan}, #{status_perkawinan}, #{status_dalam_keluarga}, #{golongan_darah}, #{is_wafat})")
	void tambahPenduduk(PendudukModel penduduk);

	@Select("select count(id) from penduduk where nik like #{nik} ")
	int selectNik(@Param("nik") String nik);
	
	@Select("select nik from penduduk where nik like #{nik} ")
	List<PendudukModel> selectNIK(@Param("nik") String nik);
	
	@Update("update penduduk set nik=#{nik}, nama=#{nama}, tempat_lahir=#{tempat_lahir}, tanggal_lahir=#{tanggal_lahir}, golongan_darah=#{golongan_darah}, agama=#{agama}, status_perkawinan=#{status_perkawinan}, pekerjaan=#{pekerjaan}, is_wni=#{is_wni}, is_wafat=#{is_wafat}, id_keluarga=#{id_keluarga}, jenis_kelamin=#{jenis_kelamin}, status_dalam_keluarga = #{status_dalam_keluarga} where nik=#{nikLama}")
	void ubahPenduduk(@Param("nik") String nik, @Param("nama") String nama, @Param("tempat_lahir") String tempat_lahir,
			@Param("tanggal_lahir") String tanggal_lahir, @Param("golongan_darah") String golongan_darah,
			@Param("agama") String agama, @Param("status_perkawinan") String status_perkawinan,
			@Param("pekerjaan") String pekerjaan, @Param("is_wni") String is_wni, @Param("is_wafat") String is_wafat,
			@Param("id_keluarga") String id_keluarga, @Param("jenis_kelamin") String jenis_kelamin,
			@Param("status_dalam_keluarga") String status_dalam_keluarga, @Param("nikLama") String nikLama);
	
	@Update ("update penduduk set is_wafat=#{penduduk.is_wafat} where nik=#{penduduk.nik}")
	void nonaktif(@Param("penduduk") PendudukModel penduduk);
	
	@Select("select nik, is_wafat from penduduk where id_keluarga=#{id_keluarga}")
	List<PendudukModel> selectKeluarga(@Param("id_keluarga") String id_keluarga);
	
	@Update("update keluarga set is_tidak_berlaku='1' where id=#{id}")
	void setTidakBerlaku(@Param("id") String id);
	
	@Select("select nama, nik, jenis_kelamin from penduduk where id_keluarga=#{id_keluarga}")
	List<PendudukModel> selectPenduduksByIdFam(@Param("id_keluarga") String id_keluarga);
}
