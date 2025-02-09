import { useEffect, useState } from 'react';
import { fetchProducts } from '../api/products-api';
import { Link } from 'react-router-dom';

interface Product {
	id: any;
	name: any;
	price: any;
}

const HomePage = () => {
	const [products, setProducts] = useState<Product[]>([]);

	useEffect(() => {
		const getProduct = async () => {
			try {
				const data: Product[] = await fetchProducts();
				setProducts(data);
			} catch (error) {
				console.error('Error fetching products:', error);
			}
		};
		getProduct();
	}, []);

	return (
		<div>
			<h1>Home Page</h1>
			{products.length > 0 ? (
				<ul>
					{products.map((product) => (
						<li key={product.id}>
							<h3>Name: {product.name}</h3>
							<p>Price: ${product.price}</p>
							<Link to={`/product/${product.id}`}>View Product</Link>
						</li>
					))}
				</ul>
			) : (
				<p>Loading...</p>
			)}
		</div>
	);
};

export default HomePage;
