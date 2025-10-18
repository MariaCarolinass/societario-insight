import { useState } from 'react';

const SocioDetail = ({ socio }) => {
  const [showCnaes, setShowCnaes] = useState(false);

  return (
    <div className="socio-detail">
      <div className="socio-basic-info">
        <p><strong>Nome:</strong> {socio.nome}</p>
        <p><strong>CNPJ:</strong> {socio.cnpj}</p>
        <p><strong>Participação:</strong> {socio.participacao}%</p>
      </div>
      
      {socio.nomes && socio.nomes.length > 0 && (
        <div className="nomes-section">
          <strong>Sócios do CNPJ:</strong>
          <ul>
            {socio.nomes.map((n, idx) => (
              <li key={idx}>{n}</li>
            ))}
          </ul>
        </div>
      )}

      {socio.cnaesSecundarios && socio.cnaesSecundarios.length > 0 && (
        <div className="cnaes-section">
          <div
            className="cnaes-toggle-box"
            onClick={() => setShowCnaes(!showCnaes)}
          >
            <strong>CNAEs:</strong> {socio.cnaesSecundarios[0]}
            <span className="arrow">{showCnaes ? '▼' : '▶'}</span>
          </div>

          {showCnaes && (
            <ul className="cnaes-list">
              {socio.cnaesSecundarios.map((cnae, idx) => (
                <li key={idx}>{cnae}</li>
              ))}
            </ul>
          )}
        </div>
      )}

      {socio.urlMapa && (
        <div className="map-section" style={{ backgroundColor: '#000', marginTop: '20px' }}>
          <iframe
            title="Localização do Sócio"
            src={socio.urlMapa}
            width="100%"
            height="300"
            style={{ border: 0 }}
            allowFullScreen
            loading="lazy"
          />
        </div>
      )}
    </div>
  );
};

export default SocioDetail;
