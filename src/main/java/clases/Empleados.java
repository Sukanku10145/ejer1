package clases;

import java.sql.Date;

public class Empleados implements java.io.Serializable {

	private short empNo;
	private Departamentos departamentos;
	private String apellido;
	private String oficio;
	private Short dir;
	private Date fechaAlt;
	private Float salario;
	private Float comision;

	public Empleados() {
	}

	public Empleados(short empNo, Departamentos departamentos) {
		this.empNo = empNo;
		this.departamentos = departamentos;
	}

	public Empleados(short empNo, Departamentos departamentos, String apellido, String oficio, Short dir, Date fechaAlt,
			Float salario, Float comision) {
		this.empNo = empNo;
		this.departamentos = departamentos;
		this.apellido = apellido;
		this.oficio = oficio;
		this.dir = dir;
		this.fechaAlt = fechaAlt;
		this.salario = salario;
		this.comision = comision;
	}

	public short getEmpNo() {
		return this.empNo;
	}

	public void setEmpNo(short empNo) {
		this.empNo = empNo;
	}

	public Departamentos getDepartamentos() {
		return this.departamentos;
	}

	public void setDepartamentos(Departamentos departamentos) {
		this.departamentos = departamentos;
	}

	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getOficio() {
		return this.oficio;
	}

	public void setOficio(String oficio) {
		this.oficio = oficio;
	}

	public Short getDir() {
		return this.dir;
	}

	public void setDir(Short dir) {
		this.dir = dir;
	}

	public Date getFechaAlt() {
		return this.fechaAlt;
	}

	public void setFechaAlt(Date fechaAlt) {
		this.fechaAlt = fechaAlt;
	}

	public Float getSalario() {
		return this.salario;
	}

	public void setSalario(Float salario) {
		this.salario = salario;
	}

	public Float getComision() {
		return this.comision;
	}

	public void setComision(Float comision) {
		this.comision = comision;
	}

	@Override
	public String toString() {
		String separador = "+---------------------------------------------------+%n";
		String plantilla = "| %-20s | %-26s |%n";

		return String.format(
				separador +
				plantilla + separador +
				plantilla +
				plantilla +
				plantilla +
				plantilla +
				plantilla +
				plantilla +
				plantilla +
				separador,
				"ID", empNo,
				"Apellido", apellido,
				"Oficio", oficio,
				"Director ID", (dir != null ? dir : "N/A"),
				"Fecha Alta", fechaAlt,
				"Salario", String.format("€%,.2f", salario != null ? salario : 0.0f),
				"Comisión", String.format("€%,.2f", comision != null ? comision : 0.0f),
				"Departamento", departamentos.getDnombre()
				);
	}
}
