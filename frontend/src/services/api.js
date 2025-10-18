import axios from 'axios';
import axiosRetry from 'axios-retry';

const API_BASE_URL = 'http://localhost:8081';

const api = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
});

axiosRetry(api, { retries: 3, retryDelay: axiosRetry.exponentialDelay });

export const getSocios = async (params) => {
  try {
    const response = await api.get('/socios/', { params });
    return response.data;
  } catch (error) {
    console.error('Erro ao buscar sócios:', error.response || error.message);
    throw error;
  }
};

export const getSocioDetail = async (cnpj) => {
  try {
    const response = await api.get(`/socios/cnpj/${cnpj}`);
    return response.data;
  } catch (error) {
    console.error('Erro ao buscar detalhes do sócio:', error.response || error.message);
    throw error;
  }
};
