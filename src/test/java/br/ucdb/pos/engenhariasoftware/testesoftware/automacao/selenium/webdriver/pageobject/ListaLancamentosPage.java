package br.ucdb.pos.engenhariasoftware.testesoftware.automacao.selenium.webdriver.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ListaLancamentosPage {

    private WebDriver driver;

    public ListaLancamentosPage(final WebDriver driver){
        this.driver = driver;
    }

    public void acessa(){
        driver.get("http://localhost:8080/lancamentos");
    }

    public void novoLancamento(){
        driver.findElement(By.id("novoLancamento")).click();
    }

    public boolean existeLancamento(final String descricaoLancamento, final BigDecimal valorLancamento,
                                    LocalDateTime dataHora, TipoLancamento tipo, final String categoriaLancamento){

        final String categoriaFormatada;
        if(categoriaLancamento=="ALIMENTACAO") {
            categoriaFormatada = "Alimentação";
        }
        else if(categoriaLancamento=="SALARIO") {
            categoriaFormatada = "Salário";
        }
        else if(categoriaLancamento=="LAZER") {
            categoriaFormatada = "Lazer";
        }
        else if(categoriaLancamento=="TELEFONE_INTERNET") {
            categoriaFormatada = "Telefone & Internet";
        }
        else if(categoriaLancamento=="CARRO") {
            categoriaFormatada = "Carro";
        }
        else if(categoriaLancamento=="EMPRESTIMO") {
            categoriaFormatada = "Empréstimo";
        }
        else if(categoriaLancamento=="INVESTIMENTOS") {
            categoriaFormatada = "Investimentos";
        }
        else{
            categoriaFormatada = "Outros";
        }

        DateTimeFormatter formatoDataLancamento = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String lancamentos = driver.getPageSource();
        DecimalFormat df = new DecimalFormat("#,###,##0.00");
        return (lancamentos.contains(descricaoLancamento) &&
                lancamentos.contains(df.format(valorLancamento)) &&
                lancamentos.contains(dataHora.format(formatoDataLancamento)) &&
                lancamentos.contains(tipo.getDescricao()) &&
                lancamentos.contains(categoriaFormatada));
    }
}

