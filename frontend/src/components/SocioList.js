const formatCNPJ = (cnpj) => {
  if (!cnpj) return '';
  return cnpj.replace(/^(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})$/, '$1.$2.$3/$4-$5');
};

const SocioList = ({ socios, onSocioClick, loading }) => {
  if (loading) {
    return <div className="loading">Carregando sócios...</div>;
  }

  if (!socios || socios.length === 0) {
    return <div className="no-data">Nenhum sócio encontrado com a participação mínima informada.</div>;
  }

  return (
    <div className="socio-list">
      <table className="socio-table">
        <thead>
          <tr>
            <th>Nome</th>
            <th>CNPJ</th>
            <th>Participação (%)</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody>
          {socios.map((socio, idx) => (
            <tr key={idx}>
              <td>{socio.nome}</td>
              <td>{formatCNPJ(socio.cnpj)}</td>
              <td>{socio.participacao}</td>
              <td>
                <button onClick={() => onSocioClick(socio)}>Visualizar</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default SocioList;
