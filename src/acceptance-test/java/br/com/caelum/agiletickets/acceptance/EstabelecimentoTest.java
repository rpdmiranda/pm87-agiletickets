package br.com.caelum.agiletickets.acceptance;

import java.util.concurrent.TimeUnit;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By.ByCssSelector;
import org.openqa.selenium.firefox.FirefoxDriver;

import br.com.caelum.agiletickets.acceptance.page.EstabelecimentosPage;

public class EstabelecimentoTest {

	public static String BASE_URL = "http://localhost:8080";
	private static WebDriver browser;
	private EstabelecimentosPage estabelecimentos;

	@BeforeClass
	public static void abreBrowser() {
		System.setProperty("webdriver.gecko.driver", "geckodriver");
		browser = new FirefoxDriver();
	}

	@Before
	public void setUp() throws Exception {
		estabelecimentos = new EstabelecimentosPage(browser);
	}

	@AfterClass
	public static void teardown() {
		browser.close();
	}

	@Test
	public void aoAdicionarUmEstabelecimentoDeveMostraLoNaTabela() throws Exception {
		estabelecimentos.abreListagem();

		estabelecimentos.adicioneEstabelecimento("Caelum", "R. Vergueiro, 3185");

		estabelecimentos.ultimaLinhaDeveConter("Caelum", "R. Vergueiro, 3185");
	}

	@Test
	public void aoAdicionarUmEstabelecimentoSemNomeDeveMostrarErro() throws Exception {
		estabelecimentos.abreListagem();

		estabelecimentos.adicioneEstabelecimento("", "R. Vergueiro, 3185");

		browser.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		estabelecimentos.deveMostrarErro("O nome não pode ser vazio");
	}

	@Test
	public void aoAdicionarUmEstabelecimentoSemEnderecoDeveMostrarErro() throws Exception {
		estabelecimentos.abreListagem();

		estabelecimentos.adicioneEstabelecimento("Caelum", "");

		browser.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		estabelecimentos.deveMostrarErro("O endereco não pode ser vazio");
	}

	@Test
	public void mostraQueHaEstacionamentoQuandoCadastramosQueSim() throws Exception {
		estabelecimentos.abreListagem();

		estabelecimentos.adicioneEstabelecimentoComEstacionamento(true);

		estabelecimentos.ultimaLinhaDeveTerEstacionamento(true);
	}

	@Test
	public void mostraQueNaoHaEstacionamentoQuandoCadastramosQueNao() throws Exception {
		estabelecimentos.abreListagem();

		estabelecimentos.adicioneEstabelecimentoComEstacionamento(false);

		estabelecimentos.ultimaLinhaDeveTerEstacionamento(false);
	}
	
	@Test
	public void reservaUmIngressoEmSalaQuaseLotada() throws Exception {
		browser.get(BASE_URL + "/");
		browser.findElement(ByCssSelector.cssSelector("#sessoes li a[href$='67']")).click();
		browser.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		browser.findElement(ById.id("qtde")).sendKeys("1");
		browser.findElement(ByCssSelector.cssSelector("form")).submit();
		browser.manage().timeouts().implicitlyWait(4, TimeUnit.SECONDS);
		Assert.assertTrue("Valor de 55", browser.findElement(ById.id("message")).getText().contains("55"));
	}
}
