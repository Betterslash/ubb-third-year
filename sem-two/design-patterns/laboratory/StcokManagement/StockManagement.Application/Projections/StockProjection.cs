namespace StockManagement.Application.Projections;

public class StockProjection
{
    public string Item { get; set; }
    public string LicensePlate { get; set; }
    public DateOnly BestBeforeDate { get; set; }
    public string Location { get; set; }
    public long QuantityOnHand { get; set; }
    public string Lot { get; set; }
}