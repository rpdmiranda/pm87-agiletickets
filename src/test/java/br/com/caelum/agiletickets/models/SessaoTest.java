package br.com.caelum.agiletickets.models;

import java.util.List;

import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.junit.Assert;
import org.junit.Test;

public class SessaoTest {
	
	@Test
	public void deveVender2IngressosSeHa2Vagas() {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(2);
		
		Assert.assertTrue(sessao.podeReservar(2));
	}
	
	@Test
	public void naoDeveVenderNumeroInvalidoDeIngressos() {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(2);
		
		Assert.assertFalse(sessao.podeReservar(0));
		Assert.assertFalse(sessao.podeReservar(-10));
		Assert.assertFalse(sessao.podeReservar(null));
	}
	
	
	@Test
	public void deveVender1ingressoSeHa2vagas() throws Exception {
		Sessao sessao = new Sessao();
        sessao.setTotalIngressos(2);

        Assert.assertTrue(sessao.podeReservar(1));
	}

	@Test
	public void naoDeveVender3ingressoSeHa2vagas() throws Exception {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(2);

		Assert.assertFalse(sessao.podeReservar(3));
	}

	@Test
	public void reservarIngressosDeveDiminuirONumeroDeIngressosDisponiveis() throws Exception {
		Sessao sessao = new Sessao();
		sessao.setTotalIngressos(5);

		sessao.reserva(3);
		Assert.assertEquals(2, sessao.getIngressosDisponiveis().intValue());
	}
	
	@Test
	public void deveCriarApenasUmaSessaoQuandoAsDatasForemIguais() throws Exception {
		Espetaculo espetaculo = new Espetaculo();
		List<Sessao> sessoes = espetaculo.criaSessoes(
				LocalDate.parse("2018-07-05"), 
				LocalDate.parse("2018-07-05"), 
				LocalTime.parse("22:00"),
				Periodicidade.DIARIA);
		
		Assert.assertNotNull(sessoes);
		Assert.assertEquals("Apenas uma sessão", 1, sessoes.size());
	}
	
	@Test
	public void deveCriarDoisEspetaculos() throws Exception {
		Espetaculo espetaculo = new Espetaculo();
		List<Sessao> sessoes = espetaculo.criaSessoes(
				LocalDate.parse("2018-07-05"), 
				LocalDate.parse("2018-07-12"), 
				LocalTime.parse("22:00"),
				Periodicidade.SEMANAL);
		
		Assert.assertNotNull(sessoes);
		Assert.assertEquals("Duas sessões", 2, sessoes.size());
		Assert.assertEquals(new LocalDate(2018,7,5), sessoes.get(0).getInicio().toLocalDate());
		Assert.assertEquals(new LocalDate(2018,7,12), sessoes.get(1).getInicio().toLocalDate());
	}
	
	@Test
	public void deveCriar8SessoesDiarias() {
		Espetaculo espetaculo = new Espetaculo();
		List<Sessao> sessoes = espetaculo.criaSessoes(
				LocalDate.parse("2018-07-05"), 
				LocalDate.parse("2018-07-12"), 
				LocalTime.parse("22:00"),
				Periodicidade.DIARIA);
		
		Assert.assertNotNull(sessoes);
		Assert.assertEquals("Duas sessões", 8, sessoes.size());
	}
	
	@Test
	public void naoDeveCriarDataFimMaiorDataInicioSemanal() throws Exception {
		Espetaculo espetaculo = new Espetaculo();
		List<Sessao> sessoes = espetaculo.criaSessoes(
				LocalDate.parse("2018-07-12"), 
				LocalDate.parse("2018-07-05"), 
				LocalTime.parse("22:00"),
				Periodicidade.SEMANAL);
		
		Assert.assertNotNull(sessoes);
		Assert.assertEquals("Sem sessões criadas", 0, sessoes.size());
//		Assert.assertEquals(new LocalDate(2018,7,5), sessoes.get(0).getInicio().toLocalDate());
//		Assert.assertEquals(new LocalDate(2018,7,12), sessoes.get(1).getInicio().toLocalDate());
	}	
	
	@Test
	public void naoDeveCriarDataFimMaiorDataInicioDiario() throws Exception {
		Espetaculo espetaculo = new Espetaculo();
		List<Sessao> sessoes = espetaculo.criaSessoes(
				LocalDate.parse("2018-07-12"), 
				LocalDate.parse("2018-07-05"), 
				LocalTime.parse("22:00"),
				Periodicidade.DIARIA);
		
		Assert.assertNotNull(sessoes);
		Assert.assertEquals("Sem sessões criadas", 0, sessoes.size());
	}
	
	@Test
	public void naoDeveCriarSemDataInicioDiario() throws Exception {
		Espetaculo espetaculo = new Espetaculo();
		List<Sessao> sessoes = espetaculo.criaSessoes(
				null, 
				LocalDate.parse("2018-07-05"), 
				LocalTime.parse("22:00"),
				Periodicidade.DIARIA);
		
		Assert.assertNotNull(sessoes);
		Assert.assertEquals("Sem sessões criadas", 0, sessoes.size());
	}	
	
}
