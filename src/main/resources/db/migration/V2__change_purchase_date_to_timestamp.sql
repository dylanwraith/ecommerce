-- Drop the old column
ALTER TABLE customer_order DROP COLUMN purchase_date;

-- Add the new column with TIMESTAMP type
ALTER TABLE customer_order ADD COLUMN purchase_timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;