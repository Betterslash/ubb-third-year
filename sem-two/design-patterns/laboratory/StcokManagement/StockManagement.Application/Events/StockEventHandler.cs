using MediatR;
using StockManagement.Domain.Events;

namespace StockManagement.Application.Events;

public class StockEventHandler : INotificationHandler<StockAdded>
{

    public StockEventHandler()
    {
    }

    public Task Handle(StockAdded notification, CancellationToken cancellationToken)
    {
        throw new NotImplementedException();
    }
}