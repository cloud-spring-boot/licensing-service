DROP TABLE IF EXISTS my_app.licenses;

CREATE TABLE my_app.licenses (
  license_id        VARCHAR(100) PRIMARY KEY NOT NULL,
  organization_id   TEXT                     NOT NULL,
  license_type      TEXT                     NOT NULL,
  product_name      TEXT                     NOT NULL,
  license_max       INT                      NOT NULL,
  license_allocated INT,
  comment           VARCHAR(100)
);

INSERT INTO my_app.licenses (license_id, organization_id, license_type, product_name, license_max, license_allocated)
VALUES ('177', '1', 'user', 'customer-crm-co', 100, 5);

INSERT INTO my_app.licenses (license_id, organization_id, license_type, product_name, license_max, license_allocated)
VALUES ('178', '1', 'user', 'suitability-plus', 200, 189);

INSERT INTO my_app.licenses (license_id, organization_id, license_type, product_name, license_max, license_allocated)
VALUES ('188', '2', 'user', 'HR-PowerSuite', 100, 4);

INSERT INTO my_app.licenses (license_id, organization_id, license_type, product_name, license_max, license_allocated)
VALUES ('189', '2', 'core-prod', 'WildCat Application Gateway', 16, 16);

