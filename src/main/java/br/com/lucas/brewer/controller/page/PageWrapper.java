package br.com.lucas.brewer.controller.page;

import static java.lang.String.format;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

public class PageWrapper<T> implements Page<T> {

	private Page<T> page;
	private UriComponentsBuilder uriBuilder;

	public PageWrapper(Page<T> page, HttpServletRequest request) {
		this.page = page;
		this.uriBuilder = ServletUriComponentsBuilder.fromRequest(request);
	}

	@Override
	public int getNumber() {
		return page.getNumber();
	}

	@Override
	public int getSize() {
		return page.getSize();
	}

	@Override
	public int getNumberOfElements() {
		return page.getNumberOfElements();
	}

	@Override
	public List<T> getContent() {
		return page.getContent();
	}

	@Override
	public boolean hasContent() {
		return page.hasContent();
	}

	@Override
	public Sort getSort() {
		return page.getSort();
	}

	@Override
	public boolean isFirst() {
		return page.isFirst();
	}

	@Override
	public boolean isLast() {
		return page.isLast();
	}

	@Override
	public boolean hasNext() {
		return page.hasNext();
	}

	@Override
	public boolean hasPrevious() {
		return page.hasPrevious();
	}

	@Override
	public Pageable nextPageable() {
		return page.nextPageable();
	}

	@Override
	public Pageable previousPageable() {
		return page.previousPageable();
	}

	@Override
	public Iterator<T> iterator() {
		return page.iterator();
	}

	@Override
	public int getTotalPages() {
		return page.getTotalPages();
	}

	@Override
	public long getTotalElements() {
		return page.getTotalElements();
	}

	@Override
	public <U> Page<U> map(Function<? super T, ? extends U> converter) {
		return page.map(converter);
	}

	public String urlPagina(int pagina) {
		return this.uriBuilder.replaceQueryParam("page", pagina).build(true).encode().toUriString();
	}

	public String urlOrdenada(String propriedade) {
		UriComponentsBuilder uriBuilderOrder = UriComponentsBuilder
				.fromUriString(uriBuilder.build(true).encode().toUriString());

		String sort = format("%s,%s", propriedade, inverterDirecao(propriedade));

		return uriBuilderOrder.replaceQueryParam("sort", sort).build(true).encode().toUriString();
	}

	public String inverterDirecao(String propriedade) {
		String direcao = "asc";

		Order order = page.getSort() != null ? page.getSort().getOrderFor(propriedade) : null;
		if (order != null) {
			direcao = Sort.Direction.ASC.equals(order.getDirection()) ? "desc" : "asc";
		}

		return direcao;
	}
	
	public boolean descendente(String propriedade) {
		return "asc".equals(inverterDirecao(propriedade));
	}
	
	/***
	 * Método para não exibir os icones cima/baixo ao lado do SKU e NOME para quando não existe ordenação (logo que entra na pagina /cervejas)
	 * @param propriedade
	 * @return
	 */
	@Deprecated
	public boolean ordenada(String propriedade) {
		Order order = page.getSort() != null ? page.getSort().getOrderFor(propriedade) : null;
		
		return order == null ? true : page.getSort().getOrderFor(propriedade) != null ? true : false;
	}

}
