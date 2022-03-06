namespace StockManagement.Domain.Events;

public interface IEventDispatcher
{
    void Publish(IEvent e);
}