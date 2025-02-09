import { useEffect, useState } from 'react';
import { fetchProductById } from '../api/products-api';
import { Link, useParams } from 'react-router-dom';

interface Product {
	id: any;
	name: any;
	price: any;
}

const ProductPage = () => {
	const { productId } = useParams();
	const [product, setProduct] = useState<Product | null>(null);

	useEffect(() => {
		const getProduct = async () => {
			try {
				if (productId) {
					const data: Product = await fetchProductById(productId);
					setProduct(data);
				}
			} catch (error) {
				console.error('Error fetching product:', error);
			}
		};
		getProduct();
	}, []);

	return (
		<div>
			{product ? (
				<>
					<h1>Product Page</h1>
					<h3>Name: {product.name}</h3>
					<p>Price: ${product.price}</p>
				</>
			) : (
				<p>Loading...</p>
			)}
		</div>
	);
};

export default ProductPage;
