package br.com.controledecontas.model;

public enum TipoConta {

	CREDITO {
		@Override
		public String toString() {
			return "Crédito";
		}
	},
	
	DEBITO {
		@Override
		public String toString() {
			return "Débito";
		}
	};
	
	public String getName() {
		return name();
	}
	
	public String getToString() {
		return toString();
	}
	
}
