import axios from 'axios';

const API_URL = process.env.REACT_APP_BASE_URL;

export const fetchProducts = async () => {
	const response = await axios.get(`${API_URL}/product`);
	return response.data;
};

export const fetchProductById = async (id: string) => {
	const response = await axios.get(`${API_URL}/product/${id}`);
	return response.data;
};
