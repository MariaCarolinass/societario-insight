import { useState, useEffect } from 'react';
import SocioList from './components/SocioList';
import SocioDetail from './components/SocioDetail';
import { getSocios, getSocioDetail } from './services/api';
import './App.css';

function App() {
  const [participacaoMin, setParticipacaoMin] = useState(0);
  const [socios, setSocios] = useState([]);
  const [selectedSocio, setSelectedSocio] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  const handleFilter = async (min = participacaoMin) => {
    setLoading(true);
    setError('');
    setSelectedSocio(null);

    try {
      const data = await getSocios({ participacaoMin: min });
      const sociosFormatados = data.map((s) => ({
        ...s,
        cnpj: s.cnpj.replace(/\D/g, ''),
        nome: s.nome,
      }));
      setSocios(sociosFormatados);
    } catch (err) {
      setError('Erro ao buscar sócios. Tente novamente.');
    } finally {
      setLoading(false);
    }
  };

  const handleSocioClick = async (socio) => {
    setLoading(true);
    setError('');

    try {
      const nomesDetalhes = await getSocioDetail(socio.cnpj.replace(/\D/g, ''));
      
      const socioCompleto = {
        ...socio,
        nomes: nomesDetalhes.map(n => n.nome),
      };

      setSelectedSocio(socioCompleto);
    } catch (err) {
      setError(err.response?.data?.erro || 'Erro ao buscar detalhes do sócio.');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    handleFilter(0);
  }, []);

  return (
    <div className="App">
      <header className="App-header">
        <h1>Quadro Societário</h1>
      </header>

      <main className="App-main">
        {error && <div className="error-message">{error}</div>}

        <div className="filter-section">
          <div className="filter-controls">
            <label htmlFor="participacaoMin">Participação Mínima (%):</label>
            <input
              id="participacaoMin"
              type="number"
              min="0"
              max="100"
              step="1"
              value={participacaoMin}
              onChange={(e) =>
                setParticipacaoMin(parseFloat(e.target.value) || 0)
              }
              placeholder="Ex: 20"
            />
            <button onClick={() => handleFilter(participacaoMin)} disabled={loading}>
              {loading ? 'Carregando...' : 'Filtrar'}
            </button>
          </div>

          <SocioList
            socios={socios}
            onSocioClick={handleSocioClick}
            loading={loading}
          />

          {selectedSocio && (
            <div style={{ marginTop: '40px' }}>
              <SocioDetail socio={selectedSocio} />
            </div>
          )}
        </div>
      </main>
    </div>
  );
}

export default App;
